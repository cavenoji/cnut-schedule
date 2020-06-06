package cnut.schedule.proxy.web.error;

import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@Requires(classes = {HttpClientResponseException.class, ExceptionHandler.class})
public class GlobalExceptionHandler
    implements ExceptionHandler<Exception, HttpResponse<GeneralResponse<?>>> {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Override
  public HttpResponse<GeneralResponse<?>> handle(
      final HttpRequest request, final Exception exception) {
    LOG.error(
        "Error occurred during request, principal: [{}]", request.getUserPrincipal(), exception);
    return HttpResponse.serverError();
  }
}
