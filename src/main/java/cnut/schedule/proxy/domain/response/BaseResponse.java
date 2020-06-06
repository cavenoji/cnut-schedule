package cnut.schedule.proxy.domain.response;

import cnut.schedule.proxy.domain.response.filter.FiltersData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "__type")
@JsonSubTypes({
  @Type(value = FiltersData.class, name = "VnzWeb.BetaSchedule+StudentScheduleFiltersData"),
  @Type(value = FacultyStudyGroups.class, name = "VnzWeb.BetaSchedule+StudyGroupsData")
})
@JsonPropertyOrder({"__type", "faculties", "educForms", "courses"})
public class BaseResponse {

  @JsonProperty("__type")
  protected String type;

  @JsonProperty("__type")
  public String getType() {
    return type;
  }

  @JsonProperty("__type")
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "BaseResponse{" + "type='" + type + '\'' + '}';
  }
}
