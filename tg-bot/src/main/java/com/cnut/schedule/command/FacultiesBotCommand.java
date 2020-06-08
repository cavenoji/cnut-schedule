package com.cnut.schedule.command;

import cnut.schedule.proxy.api.dto.response.Faculty;
import com.cnut.schedule.service.ScheduleService;
import io.micronaut.context.MessageSource;
import io.micronaut.context.MessageSource.MessageContext;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Singleton
public class FacultiesBotCommand extends AbstractBotCommand {

  private static final String FACULTY_ROW_FORMAT = "%s - (%s)";

  private final ScheduleService scheduleService;
  private final MessageSource messageSource;

  @Inject
  public FacultiesBotCommand(final ScheduleService scheduleService,
      MessageSource messageSource) {
    super("faculties", "faculties list");
    this.scheduleService = scheduleService;
    this.messageSource = messageSource;
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
    scheduleService
        .getFaculties()
        .doOnSuccess(
            s ->
                execute(
                    absSender,
                    new SendMessage()
                        .setChatId(chat.getId())
                        .setReplyToMessageId(message.getMessageId())
                        .setText(formatMessage(s)),
                    user))
        .subscribe();
  }

  private String formatMessage(final List<Faculty> faculties) {
    final String header = messageSource.getMessage("Faculties.list.header", MessageContext.DEFAULT)
        .orElseThrow() + System.lineSeparator();
    return header
        + faculties
        .stream()
        .map(faculty -> String.format(FACULTY_ROW_FORMAT, faculty.getValue(), faculty.getKey()))
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
