package learn.crypticRadio.data.mappers;

import learn.crypticRadio.models.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet resultSet, int i) throws SQLException {

        return new Room(
                resultSet.getInt("room_id"),
                resultSet.getString("name")
        );
    }
}
