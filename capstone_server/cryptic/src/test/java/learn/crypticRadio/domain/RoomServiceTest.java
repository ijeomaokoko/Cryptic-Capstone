package learn.crypticRadio.domain;
import learn.crypticRadio.data.MessageJdbcRepository;
import learn.crypticRadio.data.RoomJdbcRepository;
import learn.crypticRadio.models.AppUser;
import learn.crypticRadio.models.Role;
import learn.crypticRadio.domain.RoomService;
import learn.crypticRadio.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class RoomServiceTest {
    @MockBean
    RoomJdbcRepository repository;

    @Autowired
    RoomService service;


    @Test
    void shouldAddWhenValid(){
        Room expected = new Room();
        Room arg = new Room();
        arg.setRoomId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<Room> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        Room room = makeRoom();
        Result<Room> result = service.add(room);
        assertEquals(ResultType.INVALID, result.getType());

        room.setRoomId(0);
        room.setRoomName(null);
        result = service.add(room);
        assertEquals(ResultType.INVALID, result.getType());

    }

    @Test
    void shouldUpdate () {
        Room room = new Room();
        room.setRoomId(1);
        room.setRoomName("");

        when(repository.update(room)).thenReturn(true);

        Result<Room> actual = service.update(room);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdate () {
        Room room = new Room();
        room.setRoomId(1);
        when(repository.update(room)).thenReturn(true);

        Result<Room> actual = service.update(room);
        assertEquals(ResultType.INVALID, actual.getType());
    }


    private Room makeRoom() {
        Room room = new Room();
        room.setRoomId(1);
        room.setRoomName("room name not found.");
        return room;
    }




}
