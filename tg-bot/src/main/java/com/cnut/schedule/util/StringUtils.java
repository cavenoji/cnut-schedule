package com.cnut.schedule.util;

public final class StringUtils {

  private StringUtils() {
    throw new UnsupportedOperationException();
  }

  public static String capitalize(final String input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
  }
}
