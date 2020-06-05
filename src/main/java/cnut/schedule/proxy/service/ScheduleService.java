package cnut.schedule.proxy.service;

import cnut.schedule.proxy.domain.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.domain.response.Course;
import cnut.schedule.proxy.domain.response.EducForm;
import cnut.schedule.proxy.domain.response.Faculty;
import cnut.schedule.proxy.domain.response.FacultyStudyGroups;
import cnut.schedule.proxy.domain.response.filter.FiltersData;
import cnut.schedule.proxy.domain.response.ScheduleApiResponse;
import cnut.schedule.proxy.utils.StringUtils;
import io.reactivex.Maybe;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ScheduleService {

  private final AsDekanatRozkladApi asDekanatRozkladApi;

  @Inject
  public ScheduleService(@Named("asDekanatRozkladApiWrapper") AsDekanatRozkladApiWrapper asDekanatRozkladApi) {
    this.asDekanatRozkladApi = asDekanatRozkladApi;
  }

  public Maybe<FiltersData> getFiltersData(final String uniId) {
    return asDekanatRozkladApi.getFiltersData(uniId).map(ScheduleApiResponse::getD);
  }

  public Maybe<List<Faculty>> getFaculties(final String uniId) {
    return getFiltersData(uniId).map(FiltersData::getFaculties);
  }

  public Maybe<List<Course>> getCourses(final String uniId) {
    return getFiltersData(uniId).map(FiltersData::getCourses);
  }

  public Maybe<List<EducForm>> getEducationForms(final String uniId) {
    return getFiltersData(uniId).map(FiltersData::getEducForms);
  }

  public Maybe<FacultyStudyGroups> getFacultyStudyGroups(final String uniId,
      final String facultyId, final FacultyGroupsFilter facultyGroupsFilter) {
    facultyGroupsFilter.setUniId(uniId);
    facultyGroupsFilter.setFacultyId(facultyId);
    return getFacultyStudyGroups(facultyGroupsFilter);
  }

  private Maybe<FacultyStudyGroups> getFacultyStudyGroups(
      final FacultyGroupsFilter facultyGroupsFilter) {
    return asDekanatRozkladApi
        .getStudyGroups(facultyGroupsFilter.getUniId(),
            StringUtils.escapeIfNeeded(facultyGroupsFilter.getFacultyId()),
            StringUtils.valueOrNullLiteral(facultyGroupsFilter.getEducationForm()),
            StringUtils.valueOrNullLiteral(facultyGroupsFilter.getCourseId()),
            facultyGroupsFilter.isGiveStudyTypes())
        .map(ScheduleApiResponse::getD);
  }
}
