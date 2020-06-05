package cnut.schedule.proxy.utils;

public final class StringUtils {

  private StringUtils() {
    throw new UnsupportedOperationException("Cannot instantiate static class!");
  }

  public static String valueOrNullLiteral(final String value) {
    return value != null ? value : "null";
  }

  public static String escapeIfNeeded(final String value) {
    return value.startsWith("\"") && value.endsWith("\"") ? value : "\"" + value + "\"";
  }
}
