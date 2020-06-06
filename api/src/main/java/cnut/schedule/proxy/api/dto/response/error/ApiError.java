package cnut.schedule.proxy.api.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Message", "StackTrace", "ExceptionType"})
public class ApiError {

  @JsonProperty("Message")
  private String message;

  @JsonProperty("StackTrace")
  private String stackTrace;

  @JsonProperty("ExceptionType")
  private String exceptionType;

  @JsonProperty("Message")
  public String getMessage() {
    return message;
  }

  @JsonProperty("Message")
  public void setMessage(String message) {
    this.message = message;
  }

  @JsonProperty("StackTrace")
  public String getStackTrace() {
    return stackTrace;
  }

  @JsonProperty("StackTrace")
  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  @JsonProperty("ExceptionType")
  public String getExceptionType() {
    return exceptionType;
  }

  @JsonProperty("ExceptionType")
  public void setExceptionType(String exceptionType) {
    this.exceptionType = exceptionType;
  }

  @Override
  public String toString() {
    return "ApiError{"
        + "message='"
        + message
        + '\''
        + ", stackTrace='"
        + stackTrace
        + '\''
        + ", exceptionType='"
        + exceptionType
        + '\''
        + '}';
  }
}
