package cnut.schedule.proxy.api.util;

import java.util.function.Function;
import java.util.function.Supplier;

/** Helper class to work with functional interfaces. */
public final class FunctionalUtils {

  private FunctionalUtils() {
    throw new UnsupportedOperationException();
  }

  public static <T, R> Function<T, R> mapOrDefault(
      final Function<T, R> mapper, final Supplier<R> defaultValue) {
    return t -> {
      final var mapped = mapper.apply(t);
      if (mapped != null) {
        return mapped;
      }
      return defaultValue.get();
    };
  }

  public static <T, R> io.reactivex.functions.Function<T, R> rxMapOrDefault(
      final io.reactivex.functions.Function<T, R> mapper, final Supplier<R> defaultValue) {
    return t -> {
      final var mapped = mapper.apply(t);
      if (mapped != null) {
        return mapped;
      }
      return defaultValue.get();
    };
  }
}
