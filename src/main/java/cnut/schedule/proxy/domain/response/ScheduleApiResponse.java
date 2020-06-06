package cnut.schedule.proxy.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"d"})
public class ScheduleApiResponse<T extends BaseResponse> {

  @JsonProperty("d")
  private T d;

  @JsonProperty("d")
  public T getD() {
    return d;
  }

  @JsonProperty("d")
  public void setD(T d) {
    this.d = d;
  }

  @Override
  public String toString() {
    return "ScheduleApiResponse{" + "d=" + d + '}';
  }
}
