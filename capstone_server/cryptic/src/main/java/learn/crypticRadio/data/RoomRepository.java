package learn.crypticRadio.data;

import learn.crypticRadio.models.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository {
    List<Room> findByUserId(int AppUserId);
    Room findByRoomId(int roomId);
    Room add(Room room);
    @Transactional
    boolean update(Room room);
    @Transactional
    boolean deleteByRoomId(int roomId);
}