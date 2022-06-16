package learn.crypticRadio.data;
import learn.crypticRadio.data.MessageJdbcRepository;


import learn.crypticRadio.models.Message;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest


public class MessageJdbcTest {


    public final static int NEXT_ID = 4;

    @Autowired
    MessageJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Message> message = repository.findAll();
        assertNotNull(message);
        assertTrue(message.size() >= 1 && message.size() <= 4);
    }


    @Test
    void shouldFindById() {
        // all fields
        Message message = makeMessage();
        Message actual = repository.add(message);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getMessageId());

        // null dob
        message = makeMessage();
        actual = repository.add(message);
        assertNotNull(actual);
        assertEquals(NEXT_ID +  1, actual.getMessageId());
    }


    @Test
    void shouldUpdate() {
        Message message = makeMessage();
        message.setRoomId(10);
        assertFalse(repository.update(message));
        message.setRoomId(13);
        assertFalse(repository.update(message));
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

   private Message makeMessage(){
        Message message = new Message();
        //message.setMessageId(1);
        message.setMessageContent("TEST");
        message.setTimeStamp(Timestamp.valueOf("2022-06-16 11:12:12"));
        message.setUsername("john@smith.com");
        message.setRoomId(1);
        message.setUserId(1);
        return message;
   }


}
