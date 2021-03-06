package learn.crypticRadio.domain;


import learn.crypticRadio.data.*;
import learn.crypticRadio.models.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findByUserId(int AppUserId) {
        return roomRepository.findByUserId(AppUserId);
    }

    public Room findByRoomId(int roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    public Result<Room> add(Room room) {
        Result<Room> result = validateAdd(room);
        if (!result.isSuccess()) {
            System.out.println("InvalidInitial");
            return result;
        }
        room = roomRepository.add(room);
        result.setPayload(room);
        return result;
    }

    public Result<Room> update(Room room) {
        Result<Room> result = validate(room);
        if (!result.isSuccess()) {
            return result;
        }
        if (room.getRoomId() < 1) {
            result.addMessage("Need a room Id for update operation", ResultType.INVALID);
        }
        if (!roomRepository.update(room)) {
            result.addMessage("Room not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteByRoomId(int roomId) {
        return roomRepository.deleteByRoomId(roomId);
    }

    private Result<Room> validate(Room room) {
        Result<Room> result = new Result<>();
        if (room.getRoomName() == (null)) {
            result.addMessage("Room name cannot be null", ResultType.INVALID);
            return result;
        }
        if (room.getRoomName().equals("")) {
            result.addMessage("Room name cannot be empty", ResultType.INVALID);
        }
        if (room.getRoomId() < 1) {
            result.addMessage("Room id invalid", ResultType.INVALID);
        }
        return result;
    }

    private Result<Room> validateAdd(Room room) {
        Result<Room> result = new Result<>();
        if (room.getRoomName() == (null)) {
            result.addMessage("Room name cannot be null", ResultType.INVALID);
            return result;
        }
        if (room.getRoomName().equals("")) {
            result.addMessage("Room name cannot be empty", ResultType.INVALID);
        }

        return result;
    }
}
