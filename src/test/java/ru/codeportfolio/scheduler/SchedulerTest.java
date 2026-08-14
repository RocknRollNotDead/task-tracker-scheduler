package ru.codeportfolio.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.codeportfolio.scheduler.controller.KafkaConsumer;
import ru.codeportfolio.scheduler.dao.TaskRepository;
import ru.codeportfolio.scheduler.dao.UserRepository;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.dto.TaskDto;
import ru.codeportfolio.scheduler.dto.UserDto;
import ru.codeportfolio.scheduler.model.Status;
import ru.codeportfolio.scheduler.model.Task;
import ru.codeportfolio.scheduler.model.User;
import ru.codeportfolio.scheduler.service.ReportSendService;
import ru.codeportfolio.scheduler.service.RequestReportSendService;
import ru.codeportfolio.scheduler.service.TaskService;
import ru.codeportfolio.scheduler.service.UserService;
import ru.codeportfolio.scheduler.service.kafka.EmailKafkaSender;
import ru.codeportfolio.scheduler.service.kafka.RequestReportKafkaSender;
import ru.codeportfolio.scheduler.service.mapper.TasksReportMapper;
import tools.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {

    private static final User USER_1 = new User(1L, "username1", "1@g.ru");
    private static final User USER_2 = new User(2L, "username2", "2@g.ru");
    private static final User USER_3 = new User(3L, "username3", "3@g.ru");
    private static final User USER_4 = new User(4L, "username4", "4@g.ru");

    private static final Task TASK_1_IN_PROGRESS = new Task(
            1L,
            "name1",
            USER_1,
            Status.IN_PROGRESS,
            Timestamp.from(Instant.now())
    );
    private static final Task TASK_2_IN_PROGRESS = new Task(
            2L,
            "name2",
            USER_2,
            Status.IN_PROGRESS,
            Timestamp.from(Instant.now())
    );


    private static final Task TASK_3_DONE = new Task(
            3L,
            "name3",
            USER_1,
            Status.DONE,
            Timestamp.from(Instant.now())
    );


    private static final Task TASK_4_DONE_OLD = new Task(
            4L,
            "name1",
            USER_3,
            Status.DONE,
            Timestamp.from(Instant.now().minus(Duration.ofDays(2)))
    );

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    RequestReportKafkaSender requestReportKafkaSender;

    RequestReportSendService requestReportSendService;


    @BeforeEach
    void initializeServices(){
        requestReportSendService = new RequestReportSendService(
                requestReportKafkaSender, new TaskService(
                        taskRepository,
                        new TasksReportMapper(new ObjectMapper())
        ));


    }


    @Test
    void getTasksTest(){

        when(taskRepository.getTasksByTimestampAfter(any(Timestamp.class)))
                .thenReturn(List.of(TASK_3_DONE));
        when(taskRepository.getTasksByStatus(Status.IN_PROGRESS))
                .thenReturn(List.of(TASK_1_IN_PROGRESS, TASK_2_IN_PROGRESS));
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(USER_1));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(USER_2));


        requestReportSendService.sendRequest();

        verify(requestReportKafkaSender).sendRequest(new ReportRequestDto(List.of(
                new UserDto(1L, "username1", List.of(
                        new TaskDto(TASK_3_DONE.getName(), TASK_3_DONE.getStatus()),
                        new TaskDto(TASK_1_IN_PROGRESS.getName(), TASK_1_IN_PROGRESS.getStatus())
                ).toString()),
                new UserDto(2L, "username2", List.of(
                        new TaskDto(TASK_2_IN_PROGRESS.getName(), TASK_2_IN_PROGRESS.getStatus())
                ).toString())
        )));
    }



    @Test
    void sendTaskTest(){
        var kafkaConsumer = new KafkaConsumer(new ReportSendService(
                new EmailKafkaSender(kafkaTemplate, new ObjectMapper()),
                new UserService(userRepository)
        ));

        when(userRepository.findById(1L)).thenReturn(Optional.of(USER_1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(USER_2));

        kafkaConsumer.consume("text 1", 1L);
        kafkaConsumer.consume("text 2", 2L);

        verify(kafkaTemplate, times(2)).send(eq("EMAIL_SENDING_TASKS"), any());

    }




}
