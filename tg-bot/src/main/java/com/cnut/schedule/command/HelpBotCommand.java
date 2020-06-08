package com.cnut.schedule.command;

import com.cnut.schedule.util.StringUtils;
import io.micronaut.context.MessageSource;
import io.micronaut.context.MessageSource.MessageContext;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Singleton
public class HelpBotCommand extends AbstractBotCommand {

  //TODO move in configuration
  public static final List<String> AVAILABLE_COMMANDS = Arrays
      .asList("/help", "/faculties", "/groups", "/schedule");

  private final String commandDescription;

  @Inject
  public HelpBotCommand(final MessageSource messageSource) {
    super("help", getCommandDescription(messageSource));
    this.commandDescription = getCommandDescription(messageSource);
  }

  private static String getCommandDescription(
      final MessageSource messageSource) {
    final Optional<String> headerMessageOpt = getMessage(messageSource, "Help.message.header");
    if (headerMessageOpt.isEmpty()) {
      throw new RuntimeException("No header for help command description");
    }
    final String description =
        AVAILABLE_COMMANDS
            .stream()
            .map(s -> StringUtils.capitalize(s.substring(1)) + ".command.description")
            .map(s -> getMessage(messageSource, s))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.joining(System.lineSeparator()));
    return headerMessageOpt.get() + System.lineSeparator() + description;
  }

  private static Optional<String> getMessage(final MessageSource messageSource, final String code) {
    return messageSource.getMessage(code, MessageContext.DEFAULT);
  }

  @Override
  public void processMessage(
      final AbsSender absSender, final Message message, final String[] arguments) {
    final Chat chat = message.getChat();
    final User user = message.getFrom();
    if (!chat.isUserChat()) {
      LOG.info(
          "Command: [{}] with args: [{}] from user: [{}] is not from user chat: [{}], ignore",
          getCommandIdentifier(),
          arguments,
          user.getId(),
          chat.getId());
      return;
    }
    execute(
        absSender,
        new SendMessage()
            .setChatId(chat.getId())
            .setReplyToMessageId(message.getMessageId())
            .setText(this.commandDescription),
        user);
  }
}
