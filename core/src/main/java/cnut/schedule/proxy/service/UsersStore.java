package cnut.schedule.proxy.service;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.MapFormat;
import java.util.Collections;
import java.util.Map;

@ConfigurationProperties("credentials")
public class UsersStore {

  @MapFormat private Map<String, String> users;
  @MapFormat private Map<String, String> roles;

  public String getUserPassword(String username) {
    return users.get(username);
  }

  public String getUserRole(String username) {
    return roles.get(username);
  }

  public void setUsers(Map<String, String> users) {
    this.users = Collections.unmodifiableMap(users);
  }

  public void setRoles(Map<String, String> roles) {
    this.roles = Collections.unmodifiableMap(roles);
  }
}
