package com.cnut.schedule.command;

import cnut.schedule.proxy.api.dto.response.ScheduleDataRow;
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
public class ScheduleBotCommand extends AbstractBotCommand {

  //DATE(DAY_OF_WEEK)/STUDY_TIME/SUBJECT NAME/TYPE OF CLASS/ROOM NUMBER/TEACHER_NAME
  private static final String FORMAT = "%s(%s) | %s | %s | %s | %s | %s";

  private final ScheduleService scheduleService;

  @Inject
  public ScheduleBotCommand(final ScheduleService scheduleService) {
    super("schedule", null);
    this.scheduleService = scheduleService;
  }

  //TODO change logic of parsing arguments
  @Override
  public void processMessage(final AbsSender absSender, final Message message,
      final String[] arguments) {
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
    if (arguments.length < 2) {
      LOG.warn("Blahblah");
      return;
    }
    String facultyName = arguments[0].replace('+', ' ');
    String groupName = arguments[1];
    String startDate = null;
    String endDate = null;
    if (arguments.length > 3) {
      startDate = arguments[2];
      endDate = arguments[3];
    }
    scheduleService.getGroupSchedule(facultyName, groupName, startDate, endDate).subscribe(s -> {
      execute(
          absSender,
          new SendMessage()
              .setChatId(chat.getId())
              .setReplyToMessageId(message.getMessageId())
              .setText(formatMessage(s)),
          user);
    });
  }

  private String formatMessage(final List<ScheduleDataRow> scheduleDataRows) {
    return scheduleDataRows.stream().map(this::getFormattedRecord)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  private String getFormattedRecord(final ScheduleDataRow s) {
    return String
        .format(FORMAT, s.getFullDate(), s.getWeekDay(), s.getStudyTime(), s.getDiscipline(),
            s.getStudyType(), s.getCabinet(), s.getEmployee());
  }
}
