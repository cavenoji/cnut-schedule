package cnut.schedule.proxy.api.dto.filter;

import cnut.schedule.proxy.api.validation.DateFormat;
import javax.validation.constraints.NotEmpty;

public class GroupClassesFilter {

  @NotEmpty private String uniId;
  @NotEmpty private String groupId;

  @DateFormat("dd.MM.yyyy")
  private String startDate;

  @DateFormat("dd.MM.yyyy")
  private String endDate;

  private String studyTypeId;

  public String getUniId() {
    return uniId;
  }

  public void setUniId(String uniId) {
    this.uniId = uniId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStudyTypeId() {
    return studyTypeId;
  }

  public void setStudyTypeId(String studyTypeId) {
    this.studyTypeId = studyTypeId;
  }

  @Override
  public String toString() {
    return "GroupClassesFilter{"
        + "uniId='"
        + uniId
        + '\''
        + ", groupId='"
        + groupId
        + '\''
        + ", startDate='"
        + startDate
        + '\''
        + ", endDate='"
        + endDate
        + '\''
        + ", studyTypeId='"
        + studyTypeId
        + '\''
        + '}';
  }
}
