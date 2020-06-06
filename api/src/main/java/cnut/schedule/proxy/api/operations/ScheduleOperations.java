package cnut.schedule.proxy.api.operations;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Course;
import cnut.schedule.proxy.api.dto.response.EducForm;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import io.reactivex.Maybe;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface ScheduleOperations {

  Maybe<GeneralResponse<FiltersData>> getFiltersData(String uniId);

  Maybe<GeneralResponse<List<Faculty>>> getFaculties(String uniId);

  Maybe<GeneralResponse<List<Course>>> getCourses(String uniId);

  Maybe<GeneralResponse<List<EducForm>>> getEducationForms(String uniId);

  Maybe<GeneralResponse<FacultyStudyGroups>> getFacultyStudyGroups(
      String uniId, String facultyId, @NotNull FacultyGroupsFilter facultyGroupsFilter);

  Maybe<GeneralResponse<List<ScheduleDataRow>>> getStudyGroupClasses(
      @NotEmpty String uniId,
      @NotEmpty String groupId,
      @NotNull GroupClassesFilter groupClassesFilter);

  Maybe<GeneralResponse<List<ScheduleDataRow>>> getStudyGroupClasses(@NotEmpty String uniId,
      @NotEmpty String facultyName, String groupName, GroupClassesFilter groupClassesFilter);
}
