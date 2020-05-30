package com.cnut.schedule.web.rest;

import com.cnut.schedule.domain.schedule.filters.FiltersData;
import com.cnut.schedule.service.ScheduleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import javax.inject.Inject;

@Validated
@Controller("/api/schedule")
@Secured(SecurityRule.IS_ANONYMOUS)
public class ScheduleResource {

  private final ScheduleService scheduleService;

  @Inject
  public ScheduleResource(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Get("/{uniId}/filters")
  public Maybe<FiltersData> getFiltersData(@PathVariable("uniId") final String uniId) {
    return scheduleService.getFiltersData(uniId);
  }

  @Get("/{uniId}/faculties")
  public Flowable
}
