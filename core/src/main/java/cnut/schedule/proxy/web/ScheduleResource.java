package cnut.schedule.proxy.web;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Course;
import cnut.schedule.proxy.api.dto.response.EducForm;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import cnut.schedule.proxy.api.operations.ScheduleOperations;
import cnut.schedule.proxy.service.ScheduleService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.reactivex.Maybe;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Validated
@Controller("/api/schedule/{uniId}")
@Secured(SecurityRule.IS_ANONYMOUS)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource implements ScheduleOperations {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduleResource.class);

  private final ScheduleService scheduleService;

  @Inject
  public ScheduleResource(final ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Override
  @Get("/filters")
  public Maybe<GeneralResponse<FiltersData>> getFiltersData(
      @PathVariable("uniId") final String uniId) {
    LOG.info("Get filters data request, uniId: [{}]", uniId);
    return scheduleService.getFiltersData(uniId).map(GeneralResponse::ok);
  }

  @Override
  @Get("/faculties")
  public Maybe<GeneralResponse<List<Faculty>>> getFaculties(
      @PathVariable("uniId") final String uniId) {
    LOG.info("Get faculties data request, uniId: [{}]", uniId);
    return scheduleService.getFaculties(uniId).map(GeneralResponse::ok);
  }

  @Override
  @Get("/courses")
  public Maybe<GeneralResponse<List<Course>>> getCourses(
      @PathVariable("uniId") final String uniId) {
    LOG.info("Get courses data request, uniId: [{}]", uniId);
    return scheduleService.getCourses(uniId).map(GeneralResponse::ok);
  }

  @Override
  @Get("/educ-forms")
  public Maybe<GeneralResponse<List<EducForm>>> getEducationForms(
      @PathVariable("uniId") final String uniId) {
    LOG.info("Get educ forms data request, uniId: [{}]", uniId);
    return scheduleService.getEducationForms(uniId).map(GeneralResponse::ok);
  }

  @Override
  @Get("/faculties/{facultyId}/study-groups{?facultyGroupsFilter*}")
  public Maybe<GeneralResponse<FacultyStudyGroups>> getFacultyStudyGroups(
      @NotEmpty @PathVariable("uniId") final String uniId,
      @NotEmpty @PathVariable("facultyId") final String facultyId,
      @NotNull final FacultyGroupsFilter facultyGroupsFilter) {
    LOG.info(
        "Get faculty study groups, uniId: [{}], facultyId: [{}], filter: [{}]",
        uniId,
        facultyId,
        facultyGroupsFilter);
    return scheduleService
        .getFacultyStudyGroups(uniId, facultyId, facultyGroupsFilter)
        .map(GeneralResponse::ok);
  }

  @Override
  @Get("/study-groups/{groupId}/schedule-data{?groupClassesFilter*}")
  public Maybe<GeneralResponse<List<ScheduleDataRow>>> getFacultyStudyGroups(
      @NotEmpty @PathVariable("uniId") final String uniId,
      @NotEmpty @PathVariable("groupId") final String groupId,
      @NotNull final GroupClassesFilter groupClassesFilter) {
    LOG.info(
        "Get study group schedule, uniId: [{}], groupId: [{}], filter: [{}]",
        uniId,
        groupId,
        groupClassesFilter);
    return scheduleService
        .getGroupSchedule(uniId, groupId, groupClassesFilter)
        .map(GeneralResponse::ok);
  }
}
