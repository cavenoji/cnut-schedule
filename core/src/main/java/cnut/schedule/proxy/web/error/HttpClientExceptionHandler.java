package cnut.schedule.proxy.web.error;

import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import cnut.schedule.proxy.api.dto.response.error.ApiError;
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
public class HttpClientExceptionHandler
    implements ExceptionHandler<HttpClientResponseException, HttpResponse<GeneralResponse<?>>> {

  private static final Logger LOG = LoggerFactory.getLogger(HttpClientExceptionHandler.class);

  @Override
  public HttpResponse<GeneralResponse<?>> handle(
      final HttpRequest request, final HttpClientResponseException exception) {
    LOG.error("Exception occurred during request", exception);
    return HttpResponse.<GeneralResponse<?>>status(exception.getStatus())
        .body(
            new GeneralResponse<>(
                exception.getResponse().getBody(ApiError.class).orElse(null),
                exception.getStatus().toString(),
                exception.getStatus().getCode()))
        .status(exception.getStatus());
  }
}
