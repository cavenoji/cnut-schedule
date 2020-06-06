package cnut.schedule.proxy.service;

import cnut.schedule.proxy.domain.response.FacultyStudyGroups;
import cnut.schedule.proxy.domain.response.ScheduleApiResponse;
import cnut.schedule.proxy.domain.response.filter.FiltersData;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import javax.annotation.Nullable;
import javax.inject.Named;

@Named("asDekanatRozkladApi")
@Client("${cnut-schedule-proxy.api.dekanat.base-url}")
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
      @Nullable @QueryValue("aEducationForm") final String aEducationForm,
      @Nullable @QueryValue("aCourse") final String courseId) {
    return getStudyGroups(uniId, facultyId, aEducationForm, courseId, false);
  }

  @Get("GetScheduleDataX")
  Maybe<ScheduleApiResponse> getScheduleDataX(
      @QueryValue("aVuzID") final String uniId,
      @QueryValue("aStudyGroupID") final String groupId,
      @QueryValue("aStartDate") final String startDate,
      @QueryValue("aEndData") final String endDate,
      @QueryValue("aStudyTypeId") final String studyTypeId);
}
