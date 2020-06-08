package com.cnut.schedule.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(value = "schedule-api.credentials")
public class LoginApiProperties {

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
