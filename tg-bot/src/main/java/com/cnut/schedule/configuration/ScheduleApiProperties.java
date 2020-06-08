package com.cnut.schedule.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(value = "schedule-api")
public class ScheduleApiProperties {

  private String uniId;
  private String baseUrl;

  public String getUniId() {
    return uniId;
  }

  public void setUniId(String uniId) {
    this.uniId = uniId;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
