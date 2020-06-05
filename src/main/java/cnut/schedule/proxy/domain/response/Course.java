package cnut.schedule.proxy.domain.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Key", "Value"})
public class Course {

  @JsonProperty("Key")
  private String key;

  @JsonProperty("Value")
  private String value;

  @JsonProperty("Key")
  public String getKey() {
    return key;
  }

  @JsonProperty("Key")
  public void setKey(String key) {
    this.key = key;
  }

  @JsonProperty("Value")
  public String getValue() {
    return value;
  }

  @JsonProperty("Value")
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Course{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
