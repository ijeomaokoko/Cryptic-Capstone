package learn.crypticRadio.domain;
import learn.crypticRadio.data.MessageJdbcRepository;
import learn.crypticRadio.data.MessageRepository;
import learn.crypticRadio.models.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest

public class MessageServiceTest {
    @MockBean
    MessageJdbcRepository repository;

    @Autowired
    MessageService service;

    @Test
    void shouldAddWhenValid() {
        Message expected = makeGoodMessage();
        expected.setMessageId(0);
        when(repository.add(expected)).thenReturn(expected);
        Result<Message> result = service.add(expected);

        System.out.println(result.getMessages());

        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }



    @Test
        void shouldNotAddWhenInvalid() {
            Message expected = makeBadMessage();
            when(repository.add(expected)).thenReturn(expected);
            Result<Message> result = service.add(expected);

            assertEquals(ResultType.INVALID, result.getType());
        }

        @Test
        void shouldUpdate () {
            Message message = makeGoodMessage();

            when(repository.update(message)).thenReturn(true);

            Result<Message> actual = service.update(message);
            assertEquals(ResultType.SUCCESS, actual.getType());

        }

        @Test
        void shouldNotUpdateInvalid(){
            Message message = makeBadMessage();
            when(repository.update(message)).thenReturn(true);

            Result<Message> actual = service.update(message);
            assertEquals(ResultType.INVALID, actual.getType());
        }

        private Message makeGoodMessage() {
            Message message = new Message();
            message.setRoomId(1);
            message.setUsername("john@smith.com");
            message.setUserId(1);
            message.setMessageId(1);
            message.setMessageContent("test");
            message.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
            return message;
        }

        private Message makeBadMessage() {
            Message message = new Message();
            message.setRoomId(-1);
            message.setUsername("john@smith.com");
            message.setUserId(-1);
            message.setMessageId(-1);
            message.setMessageContent("badtest");
            message.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
            return message;
        }
    }
