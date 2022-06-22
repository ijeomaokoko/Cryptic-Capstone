package learn.crypticRadio.data;

import learn.crypticRadio.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoomJdbcRepositoryTest {

    @Autowired
    RoomJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findByUserId() {
        List<Room> found = repository.findByUserId(1);
        assertNotNull(found);
        assertEquals(2,found.size());

        found = repository.findByUserId(2);
        assertNotNull(found);
        assertEquals(0,found.size());
    }

    @Test
    void findByRoomId() {

    }

    @Test
    void add() {

    }

    @Test
    void update() {

    }

    @Test
    void deleteByRoomId() {

    }
}