package cnut.schedule.proxy.integration;

import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.ScheduleApiResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.error.ApiError;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import java.util.List;
import javax.inject.Named;

@Named("asDekanatRozkladApi")
@Client(value = "${cnut-schedule-proxy.api.dekanat.base-url}", errorType = ApiError.class)
public interface AsDekanatRozkladApi {

  @Get("GetStudentScheduleFiltersData")
  Maybe<ScheduleApiResponse<FiltersData>> getFiltersData(@QueryValue("aVuzId") final String uniId);

  @Get("GetStudyGroups")
  Maybe<ScheduleApiResponse<FacultyStudyGroups>> getStudyGroups(
      @QueryValue("aVuzID") final String uniId,
      @QueryValue("aFacultyID") final String facultyId,
      @QueryValue("aEducationForm") final String educationForm,
      @QueryValue("aCourse") final String courseId,
      @QueryValue("aGiveStudyTimes") final boolean giveStudyTimes);

  @Get("GetStudyGroups")
  default Maybe<ScheduleApiResponse<FacultyStudyGroups>> getStudyGroups(
      @QueryValue("aVuzID") final String uniId,
      @QueryValue("aFacultyID") final String facultyId,
      @QueryValue("aEducationForm") final String aEducationForm,
      @QueryValue("aCourse") final String courseId) {
    return getStudyGroups(uniId, facultyId, aEducationForm, courseId, false);
  }

  @Get("GetScheduleDataX")
  Maybe<ScheduleApiResponse<List<ScheduleDataRow>>> getScheduleDataX(
      @QueryValue("aVuzID") final String uniId,
      @QueryValue("aStudyGroupID") final String groupId,
      @QueryValue("aStartDate") final String startDate,
      @QueryValue("aEndDate") final String endDate,
      @QueryValue("aStudyTypeId") final String studyTypeId);
}
