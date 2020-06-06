package cnut.schedule.proxy.web.error;

import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@Replaces(DefaultAuthorizationExceptionHandler.class)
@Requires(classes = {AuthorizationException.class, ExceptionHandler.class})
public class AuthorizationExceptionHandler
    implements ExceptionHandler<AuthorizationException, HttpResponse<GeneralResponse<?>>> {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationExceptionHandler.class);

  @Override
  public HttpResponse<GeneralResponse<?>> handle(
      final HttpRequest request, final AuthorizationException exception) {
    LOG.error(
        "Error occurred during request, principal: [{}]", request.getUserPrincipal(), exception);
    return HttpResponse.status(HttpStatus.FORBIDDEN);
  }
}
