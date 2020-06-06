package cnut.schedule.proxy.service;

import static cnut.schedule.proxy.utils.FunctionalUtils.rxMapOrDefault;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Course;
import cnut.schedule.proxy.api.dto.response.EducForm;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.ScheduleApiResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import cnut.schedule.proxy.integration.AsDekanatRozkladApi;
import cnut.schedule.proxy.integration.AsDekanatRozkladApiWrapper;
import io.reactivex.Maybe;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ScheduleService {

  private final AsDekanatRozkladApi asDekanatRozkladApi;

  @Inject
  public ScheduleService(
      @Named("asDekanatRozkladApiWrapper") AsDekanatRozkladApiWrapper asDekanatRozkladApi) {
    this.asDekanatRozkladApi = asDekanatRozkladApi;
  }

  public Maybe<FiltersData> getFiltersData(final String uniId) {
    return asDekanatRozkladApi
        .getFiltersData(uniId)
        .map(rxMapOrDefault(ScheduleApiResponse::getD, FiltersData::new));
  }

  public Maybe<List<Faculty>> getFaculties(final String uniId) {
    return getFiltersData(uniId)
        .map(rxMapOrDefault(FiltersData::getFaculties, Collections::emptyList));
  }

  public Maybe<List<Course>> getCourses(final String uniId) {
    return getFiltersData(uniId)
        .map(rxMapOrDefault(FiltersData::getCourses, Collections::emptyList));
  }

  public Maybe<List<EducForm>> getEducationForms(final String uniId) {
    return getFiltersData(uniId)
        .map(rxMapOrDefault(FiltersData::getEducForms, Collections::emptyList));
  }

  public Maybe<FacultyStudyGroups> getFacultyStudyGroups(
      final String uniId, final String facultyId, final FacultyGroupsFilter facultyGroupsFilter) {
    facultyGroupsFilter.setUniId(uniId);
    facultyGroupsFilter.setFacultyId(facultyId);
    return getFacultyStudyGroups(facultyGroupsFilter);
  }

  public Maybe<List<ScheduleDataRow>> getGroupSchedule(
      final String uniId, final String groupId, final GroupClassesFilter classesFilter) {
    classesFilter.setUniId(uniId);
    classesFilter.setGroupId(groupId);
    return getGroupSchedule(classesFilter);
  }

  private Maybe<List<ScheduleDataRow>> getGroupSchedule(
      final GroupClassesFilter groupClassesFilter) {
    return asDekanatRozkladApi
        .getScheduleDataX(
            groupClassesFilter.getUniId(),
            groupClassesFilter.getGroupId(),
            groupClassesFilter.getStartDate(),
            groupClassesFilter.getEndDate(),
            groupClassesFilter.getStudyTypeId())
        .map(rxMapOrDefault(ScheduleApiResponse::getD, Collections::emptyList));
  }

  private Maybe<FacultyStudyGroups> getFacultyStudyGroups(
      final FacultyGroupsFilter facultyGroupsFilter) {
    return asDekanatRozkladApi
        .getStudyGroups(
            facultyGroupsFilter.getUniId(),
            facultyGroupsFilter.getFacultyId(),
            facultyGroupsFilter.getEducationForm(),
            facultyGroupsFilter.getCourseId(),
            facultyGroupsFilter.isGiveStudyTypes())
        .map(rxMapOrDefault(ScheduleApiResponse::getD, FacultyStudyGroups::new));
  }
}
