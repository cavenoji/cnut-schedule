package com.cnut.schedule.service;

import com.cnut.schedule.domain.schedule.Faculty;
import com.cnut.schedule.domain.schedule.ScheduleApiResponse;
import com.cnut.schedule.domain.schedule.filters.FiltersData;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScheduleService {

  private final AsDekanatApi asDekanatApi;

  @Inject
  public ScheduleService(final AsDekanatApi asDekanatApi) {
    this.asDekanatApi = asDekanatApi;
  }

  public Maybe<FiltersData> getAllFiltersData(final String uniId) {
    return getFiltersData(uniId);
  }

  public Flowable<Faculty> getFacultiesFilter(final String uniId) {
    return Flowable.fromPublisher(getFiltersData(uniId).map(FiltersData::getFaculties));
  }

  private Maybe<FiltersData> getFiltersData(final String uniId) {
    return asDekanatApi.scheduleFilters(uniId).map(ScheduleApiResponse::getD);
  }
}
