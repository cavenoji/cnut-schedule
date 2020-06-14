package cnut.schedule.proxy.api.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.DayOfWeek;

public class DayOfWeekDeserializer extends StdDeserializer<DayOfWeek> {

  protected DayOfWeekDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public DayOfWeek deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
    final String s = p.getCodec().readValue(p, String.class);
    if (s == null || s.isEmpty() || s.isBlank()) {
      return null;
    }
    switch (s) {
      case "Понеділок":
        return DayOfWeek.MONDAY;
      case "Вівторок":
        return DayOfWeek.TUESDAY;
      case "Середа":
        return DayOfWeek.WEDNESDAY;
      case "Четвер":
        return DayOfWeek.THURSDAY;
      case "П'ятниця":
        return DayOfWeek.FRIDAY;
      case "Субота":
        return DayOfWeek.SATURDAY;
      case "Неділя":
        return DayOfWeek.SUNDAY;
      default:
        return null;
    }
  }
}
