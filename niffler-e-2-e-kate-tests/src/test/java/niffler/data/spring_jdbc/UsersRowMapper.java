package niffler.data.spring_jdbc;

import niffler.data.entity.UsersEntity;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRowMapper implements RowMapper<UsersEntity> {
    @Override
    public UsersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(UUID.fromString(rs.getString("id")));
        usersEntity.setUsername(rs.getString("username"));
        usersEntity.setCurrency(rs.getString("currency"));
        usersEntity.setFirstname(rs.getString("firstname"));
        usersEntity.setSurname(rs.getString("surname"));
        usersEntity.setPhoto(rs.getBytes("photo"));
        return usersEntity;
    }
}
