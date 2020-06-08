package com.cnut.schedule.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.event.ApplicationEvent;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.discovery.event.ServiceStartedEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Singleton
public class TelegramBotsConfiguration implements ApplicationEventListener<StartupEvent> {

  private static final Logger LOG = LoggerFactory.getLogger(TelegramBotsConfiguration.class);

  private List<BotSession> sessions = new ArrayList<>();

  private final List<LongPollingBot> pollingBots;

  static {
    ApiContextInitializer.init();
  }

  @Inject
  public TelegramBotsConfiguration(final List<LongPollingBot> pollingBots) {
    this.pollingBots = pollingBots;
  }

//  @PostConstruct
  public void start() {
    LOG.info("Starting auto config for telegram bots");
    final TelegramBotsApi api = new TelegramBotsApi();
    pollingBots.forEach(
        bot -> {
          try {
            LOG.info("Registering polling bot: {}", bot.getBotUsername());
            sessions.add(api.registerBot(bot));
          } catch (TelegramApiException e) {
            LOG.error("Failed to register bot {} due to error", bot.getBotUsername(), e);
          }
        });
  }

  @PreDestroy
  public void stop() {
    sessions.forEach(
        session -> {
          if (session != null) {
            session.stop();
          }
        });
  }

  @Override
  public void onApplicationEvent(final StartupEvent event) {
    start();
  }
}
