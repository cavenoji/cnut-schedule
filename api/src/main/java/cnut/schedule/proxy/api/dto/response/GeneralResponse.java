package cnut.schedule.proxy.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.ALWAYS)
public final class GeneralResponse<T> {

  private final T body;
  private final String message;
  private final int code;

  @JsonCreator
  public GeneralResponse(@JsonProperty("body") T body, @JsonProperty("message") String message,
      @JsonProperty("code") int code) {
    this.body = body;
    this.message = message;
    this.code = code;
  }

  public T getBody() {
    return body;
  }

  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "GeneralResponse{"
        + "body="
        + body
        + ", message='"
        + message
        + '\''
        + ", code="
        + code
        + '}';
  }

  public static <T> GeneralResponse<T> ok() {
    return new GeneralResponse<>(null, null, 200);
  }

  public static <T> GeneralResponse<T> ok(final T body) {
    return new GeneralResponse<>(body, null, 200);
  }
}
