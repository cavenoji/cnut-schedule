package com.cnut.schedule.service;

import com.cnut.schedule.domain.schedule.ScheduleApiResponse;
import com.cnut.schedule.domain.schedule.filters.FiltersData;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Client
public interface AsDekanatApi {

  @Get("GetStudentScheduleFiltersData")
  Maybe<ScheduleApiResponse<FiltersData>> scheduleFilters(@QueryValue("aVuzId") String uniId);
}
