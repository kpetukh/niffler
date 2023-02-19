package niffler.data.spring_jdbc;

import niffler.data.entity.UsersEntity;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRowMapper implements RowMapper<UsersEntity> {
    @Override
    public UsersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsersEntity result = new UsersEntity();
        result.setId(UUID.fromString(rs.getString("id")));
        result.setUsername(rs.getString("username"));
        result.setCurrency(rs.getString("currency"));
        result.setFirstname(rs.getString("firstname"));
        result.setSurname(rs.getString("surname"));
        result.setPhoto(rs.getBytes("photo"));
        return result;
    }
}
