package com.cnut.schedule.command;

import cnut.schedule.proxy.api.dto.response.Faculty;
import cnut.schedule.proxy.api.dto.response.StudyGroup;
import com.cnut.schedule.service.ScheduleService;
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
public class GroupsBotCommand extends AbstractBotCommand {

  private static final String GROUP_ROW_FORMAT = "%s - (%s)";

  private final ScheduleService scheduleService;

  @Inject
  public GroupsBotCommand(final ScheduleService scheduleService) {
    super("groups", null);
    this.scheduleService = scheduleService;
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
    final String facultyId = arguments[0];
    if (facultyId == null || facultyId.isEmpty()) {
      LOG.debug("FacultyId argument is empty for command: [{}], user: [{}], chat: [{}]",
          getCommandIdentifier(), user.getId(), chat.getId());
      execute(absSender,
          new SendMessage().setChatId(chat.getId()).setReplyToMessageId(message.getMessageId())
              .setText("Please, retry with faculty id argument"), user);
    }
    scheduleService
        .getStudyGroups(facultyId)
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

  private String formatMessage(final List<StudyGroup> groups) {
    if (groups.isEmpty()) {
      return "Result is empty";
    }
    final String header = "HEADER" + System.lineSeparator();
    return header
        + groups
        .stream()
        .map(group -> String.format(GROUP_ROW_FORMAT, group.getValue(), group.getKey()))
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
