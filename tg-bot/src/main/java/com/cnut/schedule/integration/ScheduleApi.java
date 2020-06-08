package com.cnut.schedule.integration;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Client("${schedule-api.base-url}")
public interface ScheduleApi {

  @Get("/api/schedule/{uniId}/faculties")
  Maybe<GeneralResponse<List<Faculty>>> getFaculties(@Header("Authorization") String authorization,
      @PathVariable("uniId") String uniId);

  @Get("/api/schedule/{uniId}/faculties/{facultyId}/study-groups{?facultyGroupsFilter*}")
  Maybe<GeneralResponse<FacultyStudyGroups>> getFacultyStudyGroups(
      @Header("Authorization") String authorization,
      @PathVariable("uniId") String uniId, @PathVariable("facultyId") String facultyId,
      FacultyGroupsFilter facultyGroupsFilter);

  @Get("/api/schedule/{uniId}/study-groups/{groupId}/schedule-data{?groupClassesFilter*}")
  Maybe<GeneralResponse<List<ScheduleDataRow>>> getStudyGroupClasses(
      @Header("Authorization") String authorization,
      @PathVariable("uniId") String uniId,
      @PathVariable("groupId") String groupId,
      GroupClassesFilter groupClassesFilter);

  @Get("/api/schedule/{uniId}/faculties/{facultyName}/study-groups/{groupName}/schedule-data{?groupClassesFilter*}")
  Maybe<GeneralResponse<List<ScheduleDataRow>>> getStudyGroupClasses(
      @Header("Authorization") String authorization,
      @PathVariable("uniId") String uniId,
      @PathVariable("facultyName") String facultyName,
      @PathVariable("groupName") String groupName,
      GroupClassesFilter groupClassesFilter);
}
