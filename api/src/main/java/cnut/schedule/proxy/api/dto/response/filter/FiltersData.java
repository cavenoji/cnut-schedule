package cnut.schedule.proxy.api.dto.response.filter;

import cnut.schedule.proxy.api.dto.response.Course;
import cnut.schedule.proxy.api.dto.response.EducForm;
import cnut.schedule.proxy.api.dto.response.Faculty;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FiltersData  {

  @JsonProperty("faculties")
  private List<Faculty> faculties = null;

  @JsonProperty("educForms")
  private List<EducForm> educForms = null;

  @JsonProperty("courses")
  private List<Course> courses = null;

  @JsonProperty("faculties")
  public List<Faculty> getFaculties() {
    return faculties;
  }

  @JsonProperty("faculties")
  public void setFaculties(List<Faculty> faculties) {
    this.faculties = faculties;
  }

  @JsonProperty("educForms")
  public List<EducForm> getEducForms() {
    return educForms;
  }

  @JsonProperty("educForms")
  public void setEducForms(List<EducForm> educForms) {
    this.educForms = educForms;
  }

  @JsonProperty("courses")
  public List<Course> getCourses() {
    return courses;
  }

  @JsonProperty("courses")
  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  @Override
  public String toString() {
    return "FiltersData{"
        + "faculties="
        + faculties
        + ", educForms="
        + educForms
        + ", courses="
        + courses
        + '\''
        + '}';
  }
}
