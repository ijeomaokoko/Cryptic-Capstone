package learn.crypticRadio.domain;
import learn.crypticRadio.data.MessageJdbcRepository;
import learn.crypticRadio.data.MessageRepository;
import learn.crypticRadio.models.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class MessageServiceTest {
    @MockBean
    MessageJdbcRepository repository;

    @Autowired
    MessageService service;

    @Test
    void shouldAddWhenValid() {
        Message expected = new Message();
        Message arg = new Message();
        arg.setMessageId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<Message> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }



    @Test
        void shouldNotAddWhenInvalid() {
            Message message = makeMessage();
            Result<Message> result = service.add(message);
            assertEquals(ResultType.INVALID, result.getType());

            message.setMessageId(0);
            message.setMessageContent(null);
            result = service.add(message);
            assertEquals(ResultType.INVALID, result.getType());
        }

        @Test
        void shouldUpdate () {
            Message message = new Message();
            message.setMessageId(1);
            message.setMessageContent("messageId must be set for `update` operation");

            when(repository.update(message)).thenReturn(true);

            Result<Message> actual = service.update(message);
            assertEquals(ResultType.SUCCESS, actual.getType());

        }

        @Test
        void shouldNotUpdate(){
        Message message = new Message();
        message.setMessageId(1);
        when(repository.update(message)).thenReturn(true);

        Result<Message> actual = service.update(message);
        assertEquals(ResultType.INVALID, actual.getType());
        }

        private Message makeMessage() {
            Message message = new Message();
            message.setMessageId(1);
            message.setMessageContent("messageId: %s, not found.");
            return message;
        }
    }
