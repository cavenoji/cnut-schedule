package com.cnut.schedule.domain;

import io.micronaut.context.MessageSource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.DayOfWeek;
import java.util.Locale;

@Singleton
public class DayOfWeekToStringMapper {

  private final MessageSource messageSource;

  @Inject
  public DayOfWeekToStringMapper(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String map(final DayOfWeek dayOfWeek) {
    return map(dayOfWeek, Locale.getDefault());
  }

  public String map(final DayOfWeek dayOfWeek, final Locale locale) {
    if (dayOfWeek == null) {
      return null;
    }
    final String messageCode = getMessageCode(dayOfWeek);
    return messageSource.getRequiredMessage(messageCode, MessageSource.MessageContext.of(locale));
  }

  private String getMessageCode(final DayOfWeek dayOfWeek) {
    if (dayOfWeek == null) {
      throw new IllegalArgumentException("dayOfWeek cannot be null");
    }
    return dayOfWeek + ".short.value";
  }
}
