package niffler.data.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import niffler.data.entity.UsersEntity;
import niffler.data.jdbc.DataSourceContext;

import java.sql.*;
import java.util.UUID;
import javax.sql.DataSource;

import static niffler.data.DataBase.USERDATA;

public class PostgresJdbcUsersDAO implements UsersDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PostgresJdbcUsersDAO.class);
    private final DataSource ds = DataSourceContext.INSTANCE.getDataSource(USERDATA);

    @Override
    public int addUser(UsersEntity users) {
        String sql = "INSERT INTO users (username, currency, firstname, surname) VALUES (?,?,?,?)";
        try (Connection con = ds.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, users.getUsername());
            st.setString(2, users.getCurrency());
            st.setString(3, users.getFirstname());
            st.setString(4, users.getSurname());
            return st.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(UsersEntity user) {
        String sql = "UPDATE users SET currency = ?, firstname = ?, surname = ? WHERE username = ?";
        try (Connection con = ds.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, user.getCurrency());
            st.setString(2, user.getFirstname());
            st.setString(3, user.getSurname());
            st.setString(4, user.getUsername());
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUser(UsersEntity user) {
        String sql = "DELETE from users WHERE username = ?";
        try (Connection con = ds.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, user.getUsername());
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsersEntity getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection con = ds.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                UsersEntity result = new UsersEntity();
                result.setId(UUID.fromString(resultSet.getString("id")));
                result.setUsername(resultSet.getString("username"));
                result.setCurrency(resultSet.getString("currency"));
                result.setFirstname(resultSet.getString("firstname"));
                result.setSurname(resultSet.getString("surname"));
                result.setPhoto(resultSet.getBytes("photo"));
                return result;
            } else {
                String msg = "Can`t find user by username: " + username;
                LOG.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (SQLException e) {
            LOG.error("Error while database operation", e);
            throw new RuntimeException(e);
        }
    }
}
