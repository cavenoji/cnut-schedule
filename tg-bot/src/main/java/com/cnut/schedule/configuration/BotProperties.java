package com.cnut.schedule.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(value = "schedule.bot", cliPrefix = "schedule.bot")
public class BotProperties {

  private String botUsername;
  private String botToken;

  public BotProperties() {}

  public String getBotUsername() {
    return botUsername;
  }

  public void setBotUsername(final String botUsername) {
    this.botUsername = botUsername;
  }

  public String getBotToken() {
    return botToken;
  }

  public void setBotToken(final String botToken) {
    this.botToken = botToken;
  }

  @Override
  public String toString() {
    return "BotProperties{"
        + "botUsername='"
        + botUsername
        + '\''
        + ", botToken='"
        + botToken
        + '\''
        + '}';
  }
}
