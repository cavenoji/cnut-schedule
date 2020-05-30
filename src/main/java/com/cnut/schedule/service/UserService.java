package com.cnut.schedule.service;

import com.cnut.schedule.config.Constants;
import com.cnut.schedule.domain.Authority;
import com.cnut.schedule.domain.User;
import com.cnut.schedule.repository.AuthorityRepository;
import com.cnut.schedule.repository.UserRepository;
import com.cnut.schedule.security.AuthoritiesConstants;
import com.cnut.schedule.security.SecurityUtils;
import com.cnut.schedule.service.dto.UserDTO;
import com.cnut.schedule.service.util.RandomUtil;
import com.cnut.schedule.web.rest.errors.*;

import io.micronaut.cache.CacheManager;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.security.authentication.providers.PasswordEncoder;
import io.micronaut.transaction.annotation.ReadOnly;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Singleton
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthorityRepository authorityRepository;

  private final CacheManager cacheManager;

  @Inject
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthorityRepository authorityRepository, CacheManager cacheManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authorityRepository = authorityRepository;
    this.cacheManager = cacheManager;
  }

  public User registerUser(UserDTO userDTO, String password) {
    userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
      boolean removed = removeNonActivatedUser(existingUser);
      if (!removed) {
        throw new LoginAlreadyUsedException();
      }
    });
    userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
      boolean removed = removeNonActivatedUser(existingUser);
      if (!removed) {
        throw new EmailAlreadyUsedException();
      }
    });
    User newUser = new User();
    String encryptedPassword = passwordEncoder.encode(password);
    newUser.setLogin(userDTO.getLogin().toLowerCase());
    // new user gets initially a generated password
    newUser.setPassword(encryptedPassword);
    newUser.setEmail(userDTO.getEmail().toLowerCase());
    // new user gets registration key
    newUser.setActivationKey(RandomUtil.generateActivationKey());
    Set<Authority> authorities = new HashSet<>();
    authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
    newUser.setAuthorities(authorities);
    userRepository.save(newUser);
    this.clearUserCaches(newUser);
    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  private boolean removeNonActivatedUser(User existingUser) {
    userRepository.delete(existingUser);
    userRepository.flush();
    this.clearUserCaches(existingUser);
    return true;
  }

  public User createUser(UserDTO userDTO) {
    User user = new User();
    user.setLogin(userDTO.getLogin().toLowerCase());
    user.setEmail(userDTO.getEmail().toLowerCase());
    String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
    user.setPassword(encryptedPassword);
    user.setResetKey(RandomUtil.generateResetKey());
    user.setResetDate(Instant.now());
    if (userDTO.getAuthorities() != null) {
      Set<Authority> authorities = userDTO.getAuthorities().stream()
          .map(authorityRepository::findById)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toSet());
      user.setAuthorities(authorities);
    }
    userRepository.save(user);
    this.clearUserCaches(user);
    log.debug("Created Information for User: {}", user);
    return user;
  }

  /**
   * Update basic information (email) for the current user.
   *
   * @param email email id of user.
   */
  public void updateUser(String email) {
    SecurityUtils.getCurrentUserLogin()
        .flatMap(userRepository::findOneByLogin)
        .ifPresent(user -> {
          user.setEmail(email.toLowerCase());
          this.clearUserCaches(user);
          log.debug("Changed Information for User: {}", user);
        });
  }

  /**
   * Update all information for a specific user, and return the modified user.
   *
   * @param userDTO user to update.
   * @return updated user.
   */
  public Optional<UserDTO> updateUser(UserDTO userDTO) {
    return Optional.of(userRepository
        .findById(userDTO.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(user -> {
          this.clearUserCaches(user);
          user.setLogin(userDTO.getLogin().toLowerCase());
          user.setEmail(userDTO.getEmail().toLowerCase());
          Set<Authority> managedAuthorities = user.getAuthorities();
          managedAuthorities.clear();
          userDTO.getAuthorities().stream()
              .map(authorityRepository::findById)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .forEach(managedAuthorities::add);
          user = userRepository.save(user);
          this.clearUserCaches(user);
          log.debug("Changed Information for User: {}", user);
          return user;
        })
        .map(UserDTO::new);
  }

  public void deleteUser(String login) {
    userRepository.findOneByLogin(login).ifPresent(user -> {
      userRepository.delete(user);
      this.clearUserCaches(user);
      log.debug("Deleted User: {}", user);
    });
  }

  public void changePassword(String currentClearTextPassword, String newPassword) {
    SecurityUtils.getCurrentUserLogin()
        .flatMap(userRepository::findOneByLogin)
        .ifPresent(user -> {
          String currentEncryptedPassword = user.getPassword();
          if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
            throw new InvalidPasswordException();
          }
          String encryptedPassword = passwordEncoder.encode(newPassword);
          user.setPassword(encryptedPassword);
          this.clearUserCaches(user);
          log.debug("Changed password for User: {}", user);
        });
  }

  @ReadOnly
  public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
    Page<User> userPage = userRepository.findAllByLoginNot(Constants.ANONYMOUS_USER, pageable);
    return Page
        .of(userPage.getContent().stream().map(UserDTO::new).collect(Collectors.toList()), pageable,
            userPage.getTotalSize());
  }

  @ReadOnly
  public Optional<User> getUserWithAuthoritiesByLogin(String login) {
    return userRepository.findOneByLogin(login);
  }

  @ReadOnly
  public Optional<User> getUserWithAuthorities(Long id) {
    return userRepository.findOneById(id);
  }

  @ReadOnly
  public Optional<User> getUserWithAuthorities() {
    return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
  }

  /**
   * Not activated users should be automatically deleted after 3 days.
   * <p>
   * This is scheduled to get fired everyday, at 01:00 (am).
   */
  @Scheduled(cron = "0 0 1 * * ?")
  public void removeNotActivatedUsers() {
    userRepository
        .findAllByCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
        .forEach(user -> {
          log.debug("Deleting not activated user {}", user.getLogin());
          userRepository.delete(user);
          this.clearUserCaches(user);
        });
  }

  /**
   * Gets a list of all the authorities.
   *
   * @return a list of all the authorities.
   */
  public List<String> getAuthorities() {
    return authorityRepository.findAll().stream()
        .map(Authority::getName).collect(Collectors.toList());
  }

  private void clearUserCaches(User user) {
    Objects.requireNonNull(
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).invalidate(user.getLogin());
    Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE))
        .invalidate(user.getEmail());
  }

  public Optional<String> getCurrentUserLogin() {
    return SecurityUtils.getCurrentUserLogin();
  }
}
