package com.cnut.schedule.bot;

import com.cnut.schedule.configuration.BotProperties;
import io.micronaut.context.annotation.Value;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.objects.Update;

@Singleton
public class ScheduleBot extends TelegramLongPollingCommandBot {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduleBot.class);

//    private final BotProperties configurationProperties;
  private final String userName;
  private final String getBotToken;

  @Inject
  public ScheduleBot(final BotProperties botProperties,
      final List<IBotCommand> commandHandlers) {
    super(ApiContext.getInstance(DefaultBotOptions.class), false);
    this.userName = botProperties.getBotUsername();
    this.getBotToken = botProperties.getBotToken();
//    System.err.println(configurationProperties);
    for (final IBotCommand command : commandHandlers) {
      register(command);
    }
  }

  @Override
  public String getBotUsername() {
//    return userName;
    return userName;
  }

  @Override
  public void processNonCommandUpdate(final Update update) {
    LOG.info("Log non-command update: [{}]", update);
  }

  @Override
  public String getBotToken() {
//    return getBotToken;
    return getBotToken;
  }
}
