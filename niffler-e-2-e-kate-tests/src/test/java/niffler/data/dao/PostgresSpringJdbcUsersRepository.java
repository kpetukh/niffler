package niffler.data.dao;

import niffler.data.entity.UserAuthEntity;
import niffler.data.jdbc.DataSourceContext;
import niffler.data.spring_jdbc.AuthUserEntityRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static niffler.data.DataBase.AUTH;
import static niffler.data.entity.UserAuthEntity.Authority.READ;
import static niffler.data.entity.UserAuthEntity.Authority.WRITE;

public class PostgresSpringJdbcUsersRepository implements UsersRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceContext.INSTANCE.getDataSource(AUTH));

    public UserAuthEntity createUserWithReadAuthority(UserAuthEntity userAuthEntity) {
        addUser(userAuthEntity);
        userAuthEntity = getByUsername(userAuthEntity.getUsername());
        jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?,?)", userAuthEntity.getId(), READ.toString());
        return userAuthEntity;
    }

    public UserAuthEntity createUserWithReadAndWriteAuthority(UserAuthEntity userAuthEntity) {
        userAuthEntity = createUserWithReadAuthority(userAuthEntity);
        jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?,?)", userAuthEntity.getId(), WRITE.toString());
        return userAuthEntity;
    }

    @Override
    public UserAuthEntity getByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?",
                new AuthUserEntityRowMapper(),
                username);
    }

    private int addUser(UserAuthEntity userAuthEntity) {
        return jdbcTemplate.update("INSERT INTO users " +
                        "(username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                userAuthEntity.getUsername(),
                encodePassword(userAuthEntity.getPassword()),
                userAuthEntity.getIsEnabled(),
                userAuthEntity.getIsAccountNonExpired(),
                userAuthEntity.getIsAccountNonLocked(),
                userAuthEntity.getIsCredentialsNonExpired());
    }

    public static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
