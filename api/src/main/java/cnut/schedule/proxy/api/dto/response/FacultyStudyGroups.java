package cnut.schedule.proxy.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"studyGroups", "studyTypes"})
public class FacultyStudyGroups {

  @JsonProperty("studyGroups")
  private List<StudyGroup> studyGroups = null;

  @JsonProperty("studyTypes")
  private List<StudyType> studyTypes = null;

  @JsonProperty("studyGroups")
  public List<StudyGroup> getStudyGroups() {
    return studyGroups;
  }

  @JsonProperty("studyGroups")
  public void setStudyGroups(List<StudyGroup> studyGroups) {
    this.studyGroups = studyGroups;
  }

  @JsonProperty("studyTypes")
  public List<StudyType> getStudyTypes() {
    return studyTypes;
  }

  @JsonProperty("studyTypes")
  public void setStudyTypes(List<StudyType> studyTypes) {
    this.studyTypes = studyTypes;
  }
}
