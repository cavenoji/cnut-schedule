package cnut.schedule.proxy.validation;

import cnut.schedule.proxy.api.validation.DateFormat;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

@Factory
public class DateFormatValidatorFactory {

  private final Map<String, SimpleDateFormat> formatsCache = new HashMap<>();

  @Singleton
  public ConstraintValidator<DateFormat, CharSequence> dateFormatValidator() {
    return (value, annotationMetadata, context) -> {
      if (value == null || value.length() == 0) {
        return true;
      }
      final var pattern = annotationMetadata.stringValue("pattern");
      if (pattern.isEmpty()) {
        return true;
      }
      final var patternValue = pattern.get();
      final var dateFormat =
          formatsCache.computeIfAbsent(patternValue, (v) -> createFormatter(patternValue));
      return validate(value, dateFormat);
    };
  }

  private boolean validate(CharSequence value, SimpleDateFormat dateFormat) {
    try {
      dateFormat.parse(value.toString());
      return true;
    } catch (final ParseException e) {
      return false;
    }
  }

  private static SimpleDateFormat createFormatter(final String patternValue) {
    final var format = new SimpleDateFormat(patternValue);
    format.setLenient(false);
    return format;
  }
}
