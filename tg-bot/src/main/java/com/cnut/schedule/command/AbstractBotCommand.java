package com.cnut.schedule.command;

import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractBotCommand extends BotCommand {

  protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

  /**
   * Construct a command
   *
   * @param commandIdentifier the unique identifier of this command (e.g. the command string to
   *     enter into chat)
   * @param description the description of this command
   */
  public AbstractBotCommand(final String commandIdentifier, final String description) {
    super(commandIdentifier, description);
  }

  public void execute(AbsSender sender, SendMessage message, User user) {
    try {
      sender.execute(message);
      LOG.info(
          "Command successfully processed, user: [{}], command: [{}]",
          user.getId(),
          getCommandIdentifier());
    } catch (TelegramApiException e) {
      LOG.error(
          "Error occurred during command execute, user: [{}], command: [{}]",
          user.getId(),
          getCommandIdentifier(),
          e);
    }
  }

  @Override
  public abstract void processMessage(AbsSender absSender, Message message, String[] arguments);

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    // NOOP
  }

  protected void dispose(final Disposable disposable) {
    disposable.dispose();
  }
}
