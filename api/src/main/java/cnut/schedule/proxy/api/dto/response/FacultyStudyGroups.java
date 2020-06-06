package cnut.schedule.proxy.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FacultyStudyGroups extends BaseResponse {

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
