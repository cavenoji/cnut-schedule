package cnut.schedule.proxy.integration;

import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.ScheduleApiResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.filter.FiltersData;
import cnut.schedule.proxy.utils.StringUtils;
import io.reactivex.Maybe;
import java.util.List;
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
  public Maybe<ScheduleApiResponse<FacultyStudyGroups>> getStudyGroups(
      String uniId,
      String facultyId,
      String educationForm,
      String courseId,
      boolean giveStudyTimes) {
    return asDekanatRozkladApi.getStudyGroups(
        uniId,
        StringUtils.escapeIfNeeded(facultyId),
        StringUtils.valueOrNullLiteral(educationForm),
        StringUtils.valueOrNullLiteral(courseId),
        giveStudyTimes);
  }

  @Override
  public Maybe<ScheduleApiResponse<List<ScheduleDataRow>>> getScheduleDataX(
      String uniId, String groupId, String startDate, String endDate, String studyTypeId) {
    return asDekanatRozkladApi.getScheduleDataX(
        uniId,
        StringUtils.escapeIfNeeded(groupId),
        StringUtils.valueOrNullLiteral(startDate),
        StringUtils.valueOrNullLiteral(endDate),
        StringUtils.valueOrNullLiteral(StringUtils.escapeIfNeeded(studyTypeId)));
  }
}
