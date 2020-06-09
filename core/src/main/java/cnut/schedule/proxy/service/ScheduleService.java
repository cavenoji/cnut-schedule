package cnut.schedule.proxy.service;

import static cnut.schedule.proxy.api.util.FunctionalUtils.rxMapOrDefault;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Course;
import cnut.schedule.proxy.api.dto.response.EducForm;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.ScheduleApiResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.StudyGroup;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import cnut.schedule.proxy.integration.AsDekanatRozkladApi;
import cnut.schedule.proxy.integration.AsDekanatRozkladApiWrapper;
import io.reactivex.Maybe;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ScheduleService {

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private static final String DEFAULT_START_DATE =
      DATE_FORMAT.format(LocalDate.now().minusYears(1L));
  private static final String DEFAULT_END_DATE = DATE_FORMAT.format(LocalDate.now().plusYears(1L));

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

  public Maybe<List<ScheduleDataRow>> getGroupSchedule(
      final String uniId,
      final String facultyName,
      final String groupName,
      final GroupClassesFilter classesFilter) {
    return getFaculties(uniId)
        .flatMap(
            faculties ->
                faculties
                    .stream()
                    .filter(faculty -> facultyName.equals(faculty.getValue()))
                    .findFirst()
                    .map(Maybe::just)
                    .orElseGet(Maybe::empty))
        .flatMap(faculty -> getStudyGroup(uniId, groupName, faculty))
        .flatMap(studyGroup -> getGroupSchedule(uniId, studyGroup.getKey(), classesFilter));
  }

  private Maybe<? extends StudyGroup> getStudyGroup(
      final String uniId, final String groupName, Faculty faculty) {
    final Maybe<List<StudyGroup>> studyGroups =
        getFacultyStudyGroups(uniId, faculty.getKey(), new FacultyGroupsFilter())
            .map(rxMapOrDefault(FacultyStudyGroups::getStudyGroups, Collections::emptyList));
    return studyGroups.flatMap(
        groups -> {
          final Optional<StudyGroup> first =
              groups.stream().filter(group -> groupName.equals(group.getValue())).findFirst();
          return first.map(Maybe::just).orElseGet(Maybe::empty);
        });
  }

  private Maybe<List<ScheduleDataRow>> getGroupSchedule(
      final GroupClassesFilter groupClassesFilter) {
    return asDekanatRozkladApi
        .getScheduleDataX(
            groupClassesFilter.getUniId(),
            groupClassesFilter.getGroupId(),
            groupClassesFilter.getStartDate() != null
                ? groupClassesFilter.getStartDate()
                : getDefaultStartDate(),
            groupClassesFilter.getEndDate() != null
                ? groupClassesFilter.getEndDate()
                : getDefaultEndDate(),
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

  private String getDefaultStartDate() {
    return DATE_FORMAT.format(LocalDate.now().minusYears(1L));
  }

  private String getDefaultEndDate() {
    return DATE_FORMAT.format(LocalDate.now().plusYears(1L));
  }
}
