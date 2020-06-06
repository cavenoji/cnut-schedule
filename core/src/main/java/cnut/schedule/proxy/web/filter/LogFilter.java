package cnut.schedule.proxy.web.filter;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Filter("/**")
public class LogFilter extends OncePerRequestHttpServerFilter {

  private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);

  @Override
  public Publisher<MutableHttpResponse<?>> doFilterOnce(
      final HttpRequest<?> request, final ServerFilterChain chain) {
    return trace(request)
        .switchMap(aBoolean -> chain.proceed(request))
        .doOnNext(res -> res.getHeaders().add("X-Trace-Enabled", "true"));
  }

  private Flowable<Boolean> trace(HttpRequest<?> request) {
    return Flowable.fromCallable(
            () -> {
              if (LOG.isDebugEnabled()) {
                LOG.debug("START | method {} : path: {}", request.getMethod(), request.getPath());
              }
              return true;
            })
        .subscribeOn(Schedulers.io());
  }
}
