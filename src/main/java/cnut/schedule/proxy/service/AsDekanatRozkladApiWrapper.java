package cnut.schedule.proxy.service;

import cnut.schedule.proxy.domain.response.FacultyStudyGroups;
import cnut.schedule.proxy.domain.response.ScheduleApiResponse;
import cnut.schedule.proxy.domain.response.filter.FiltersData;
import cnut.schedule.proxy.utils.StringUtils;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("asDekanatRozkladApiWrapper")
public class AsDekanatRozkladApiWrapper implements AsDekanatRozkladApi {

  private final AsDekanatRozkladApi asDekanatRozkladApi;

  @Inject
  public AsDekanatRozkladApiWrapper(
      @Named("asDekanatRozkladApi") final AsDekanatRozkladApi asDekanatRozkladApi) {
    this.asDekanatRozkladApi = asDekanatRozkladApi;
  }

  @Override
  public Maybe<ScheduleApiResponse<FiltersData>> getFiltersData(String uniId) {
    return asDekanatRozkladApi.getFiltersData(uniId);
  }

  @Override
  public Maybe<ScheduleApiResponse<FacultyStudyGroups>> getStudyGroups(String uniId,
      String facultyId, String educationForm, String courseId,
      boolean giveStudyTimes) {
    return asDekanatRozkladApi
        .getStudyGroups(uniId,
            StringUtils.escapeIfNeeded(facultyId),
            StringUtils.valueOrNullLiteral(educationForm),
            StringUtils.valueOrNullLiteral(courseId), giveStudyTimes);
  }

  @Override
  public Maybe<ScheduleApiResponse> getScheduleDataX(String uniId, String groupId,
      String startDate, String endDate, String studyTypeId) {
    return null;
  }
}
