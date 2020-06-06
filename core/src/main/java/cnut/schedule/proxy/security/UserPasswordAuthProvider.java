package cnut.schedule.proxy.security;

import cnut.schedule.proxy.service.UsersStore;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.HttpAuthenticationProvider;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class UserPasswordAuthProvider implements HttpAuthenticationProvider {

  private final UsersStore usersStore;

  @Inject
  public UserPasswordAuthProvider(final UsersStore usersStore) {
    this.usersStore = usersStore;
  }

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      final HttpRequest<?> request, final AuthenticationRequest<?, ?> authenticationRequest) {
    return Flowable.create(
        emitter -> {
          final var username = authenticationRequest.getIdentity().toString();
          if (authenticationRequest.getSecret().equals(usersStore.getUserPassword(username))) {
            emitter.onNext(
                new UserDetails(
                    username, Collections.singletonList(usersStore.getUserRole(username))));
          } else {
            emitter.onError(new AuthenticationException(new AuthenticationFailed()));
          }
          emitter.onComplete();
        },
        BackpressureStrategy.ERROR);
  }
}
