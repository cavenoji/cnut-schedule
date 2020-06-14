package cnut.schedule.proxy.api.dto.response;

import cnut.schedule.proxy.api.jackson.DayOfWeekDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.DayOfWeek;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "study_time",
  "study_time_begin",
  "study_time_end",
  "week_day",
  "full_date",
  "discipline",
  "study_type",
  "cabinet",
  "employee",
  "study_subgroup"
})
public class ScheduleDataRow {

  @JsonProperty("study_time")
  private String studyTime;

  @JsonProperty("study_time_begin")
  private String studyTimeBegin;

  @JsonProperty("study_time_end")
  private String studyTimeEnd;

  @JsonProperty("week_day")
  @JsonDeserialize(using = DayOfWeekDeserializer.class)
  private DayOfWeek weekDay;

  @JsonProperty("full_date")
  private String fullDate;

  @JsonProperty("discipline")
  private String discipline;

  @JsonProperty("study_type")
  private String studyType;

  @JsonProperty("cabinet")
  private String cabinet;

  @JsonProperty("employee")
  private String employee;

  @JsonProperty("study_subgroup")
  private Object studySubgroup;

  @JsonProperty("study_time")
  public String getStudyTime() {
    return studyTime;
  }

  @JsonProperty("study_time")
  public void setStudyTime(String studyTime) {
    this.studyTime = studyTime;
  }

  @JsonProperty("study_time_begin")
  public String getStudyTimeBegin() {
    return studyTimeBegin;
  }

  @JsonProperty("study_time_begin")
  public void setStudyTimeBegin(String studyTimeBegin) {
    this.studyTimeBegin = studyTimeBegin;
  }

  @JsonProperty("study_time_end")
  public String getStudyTimeEnd() {
    return studyTimeEnd;
  }

  @JsonProperty("study_time_end")
  public void setStudyTimeEnd(String studyTimeEnd) {
    this.studyTimeEnd = studyTimeEnd;
  }

  @JsonProperty("week_day")
  public DayOfWeek getWeekDay() {
    return weekDay;
  }

  @JsonProperty("week_day")
  public void setWeekDay(DayOfWeek weekDay) {
    this.weekDay = weekDay;
  }

  @JsonProperty("full_date")
  public String getFullDate() {
    return fullDate;
  }

  @JsonProperty("full_date")
  public void setFullDate(String fullDate) {
    this.fullDate = fullDate;
  }

  @JsonProperty("discipline")
  public String getDiscipline() {
    return discipline;
  }

  @JsonProperty("discipline")
  public void setDiscipline(String discipline) {
    this.discipline = discipline;
  }

  @JsonProperty("study_type")
  public String getStudyType() {
    return studyType;
  }

  @JsonProperty("study_type")
  public void setStudyType(String studyType) {
    this.studyType = studyType;
  }

  @JsonProperty("cabinet")
  public String getCabinet() {
    return cabinet;
  }

  @JsonProperty("cabinet")
  public void setCabinet(String cabinet) {
    this.cabinet = cabinet;
  }

  @JsonProperty("employee")
  public String getEmployee() {
    return employee;
  }

  @JsonProperty("employee")
  public void setEmployee(String employee) {
    this.employee = employee;
  }

  @JsonProperty("study_subgroup")
  public Object getStudySubgroup() {
    return studySubgroup;
  }

  @JsonProperty("study_subgroup")
  public void setStudySubgroup(Object studySubgroup) {
    this.studySubgroup = studySubgroup;
  }
}
