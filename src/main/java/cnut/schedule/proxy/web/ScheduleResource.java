package cnut.schedule.proxy.web;

import cnut.schedule.proxy.domain.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.domain.response.Course;
import cnut.schedule.proxy.domain.response.EducForm;
import cnut.schedule.proxy.domain.response.Faculty;
import cnut.schedule.proxy.domain.response.FacultyStudyGroups;
import cnut.schedule.proxy.domain.response.filter.FiltersData;
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
public class ScheduleResource {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduleResource.class);

  private final ScheduleService scheduleService;

  @Inject
  public ScheduleResource(final ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Get("/filters")
  public Maybe<FiltersData> getFiltersData(@PathVariable("uniId") final String uniId) {
    LOG.info("Get filters data request, uniId: [{}]", uniId);
    return scheduleService.getFiltersData(uniId);
  }

  @Get("/faculties")
  public Maybe<List<Faculty>> getFaculties(@PathVariable("uniId") final String uniId) {
    LOG.info("Get faculties data request, uniId: [{}]", uniId);
    return scheduleService.getFaculties(uniId);
  }

  @Get("/courses")
  public Maybe<List<Course>> getCourses(@PathVariable("uniId") final String uniId) {
    LOG.info("Get courses data request, uniId: [{}]", uniId);
    return scheduleService.getCourses(uniId);
  }

  @Get("/educ-forms")
  public Maybe<List<EducForm>> getEducationForms(@PathVariable("uniId") final String uniId) {
    LOG.info("Get educ forms data request, uniId: [{}]", uniId);
    return scheduleService.getEducationForms(uniId);
  }

  @Get("/faculties/{facultyId}/study-groups{?facultyGroupsFilter*}")
  public Maybe<FacultyStudyGroups> getFacultyStudyGroups(
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
        .doOnError(Throwable::printStackTrace);
  }
}
