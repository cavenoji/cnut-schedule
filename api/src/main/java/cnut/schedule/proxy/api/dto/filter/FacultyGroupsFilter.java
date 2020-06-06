package cnut.schedule.proxy.api.dto.filter;

public class FacultyGroupsFilter {

  private String uniId;
  private String facultyId;
  private String educationForm;
  private String courseId;
  private boolean giveStudyTypes;

  public String getUniId() {
    return uniId;
  }

  public void setUniId(String uniId) {
    this.uniId = uniId;
  }

  public String getFacultyId() {
    return facultyId;
  }

  public void setFacultyId(String facultyId) {
    this.facultyId = facultyId;
  }

  public String getEducationForm() {
    return educationForm;
  }

  public void setEducationForm(String educationForm) {
    this.educationForm = educationForm;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public boolean isGiveStudyTypes() {
    return giveStudyTypes;
  }

  public void setGiveStudyTypes(boolean giveStudyTypes) {
    this.giveStudyTypes = giveStudyTypes;
  }

  @Override
  public String toString() {
    return "FacultyGroupsFilter{"
        + "uniId='"
        + uniId
        + '\''
        + ", facultyId='"
        + facultyId
        + '\''
        + ", educationForm='"
        + educationForm
        + '\''
        + ", courseId='"
        + courseId
        + '\''
        + ", giveStudyTypes="
        + giveStudyTypes
        + '}';
  }
}
