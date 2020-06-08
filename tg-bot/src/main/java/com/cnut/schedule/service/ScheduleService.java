package com.cnut.schedule.service;

import cnut.schedule.proxy.api.dto.filter.FacultyGroupsFilter;
import cnut.schedule.proxy.api.dto.filter.GroupClassesFilter;
import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.FacultyStudyGroups;
import cnut.schedule.proxy.api.dto.response.GeneralResponse;
import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
import cnut.schedule.proxy.api.dto.response.StudyGroup;
import cnut.schedule.proxy.api.util.FunctionalUtils;
import com.cnut.schedule.configuration.LoginApiProperties;
import com.cnut.schedule.configuration.ScheduleApiProperties;
import com.cnut.schedule.integration.LoginApi;
import com.cnut.schedule.integration.ScheduleApi;
import io.reactivex.Maybe;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScheduleService {

  private String token;
  private long nextTokenGenerationTimeInEpochSeconds;
  private final Map<String, String> usernamePassword;

  private final String uniId;
  private final ScheduleApi scheduleApi;
  private final LoginApi loginApi;

  @Inject
  public ScheduleService(final LoginApiProperties loginApiProperties,
      final ScheduleApiProperties scheduleApiProperties,
      final ScheduleApi scheduleApi,
      final LoginApi loginApi) {
    this.usernamePassword = Map.of("username", loginApiProperties.getUsername(), "password",
        loginApiProperties.getPassword());
    System.out.println(usernamePassword);
    this.uniId = scheduleApiProperties.getUniId();
    this.scheduleApi = scheduleApi;
    this.loginApi = loginApi;
  }

  @PostConstruct
  public void init() {
    refreshToken();
  }

  public Maybe<List<Faculty>> getFaculties() {
    return scheduleApi
        .getFaculties(getRefreshedToken(), uniId)
        .map(FunctionalUtils.rxMapOrDefault(GeneralResponse::getBody, Collections::emptyList));
  }

  public Maybe<List<StudyGroup>> getStudyGroups(final String facultyId) {
    return scheduleApi
        .getFacultyStudyGroups(getRefreshedToken(), uniId, facultyId, new FacultyGroupsFilter())
        .map(r -> {
          final FacultyStudyGroups body = r.getBody();
          if (body == null) {
            return Collections.emptyList();
          }
          return body.getStudyGroups() == null ? Collections.emptyList() : body.getStudyGroups();
        });
  }

  public Maybe<List<ScheduleDataRow>> getGroupSchedule(final String facultyName,
      final String groupName, final String startDate, final String endDate) {
    final GroupClassesFilter filter = new GroupClassesFilter();
    filter.setStartDate(startDate);
    filter.setEndDate(endDate);
    return scheduleApi
        .getStudyGroupClasses(getRefreshedToken(), uniId, facultyName, groupName, filter)
        .map(FunctionalUtils.rxMapOrDefault(GeneralResponse::getBody, Collections::emptyList));
  }

  private String getRefreshedToken() {
    if (Instant.now().getEpochSecond() >= nextTokenGenerationTimeInEpochSeconds) {
      refreshToken();
    }
    return token;
  }

  private void refreshToken() {
    long now = Instant.now().getEpochSecond();
    loginApi.login(usernamePassword).subscribe(response -> {
      token = "Bearer " + response.get("access_token");
      System.out.println(response);
      nextTokenGenerationTimeInEpochSeconds = now + Long.parseLong(response.get("expires_in"));
    });
  }
}
