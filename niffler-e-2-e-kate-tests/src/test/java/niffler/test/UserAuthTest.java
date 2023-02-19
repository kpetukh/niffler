package niffler.test;

import org.junit.jupiter.api.Test;
import niffler.data.dao.UsersRepository;
import niffler.data.entity.UserAuthEntity;
import niffler.data.dao.PostgresSpringJdbcUsersRepository;

public class UserAuthTest {

    UsersRepository usersRepository = new PostgresSpringJdbcUsersRepository();

    UserAuthEntity userAuthEntityWrite = UserAuthEntity.builder()
            .username("UserWrite")
            .password("password-write")
            .build();

    UserAuthEntity userAuthEntityRead = UserAuthEntity.builder()
            .username("UserRead")
            .password("password-read")
            .build();

    @Test
    void createUsers() {
        UserAuthEntity createdWrite = usersRepository.createUserWithReadAndWriteAuthority(userAuthEntityWrite);
        UserAuthEntity createdRead = usersRepository.createUserWithReadAuthority(userAuthEntityRead);

        System.out.println(createdWrite);
        System.out.println(createdRead);
    }
}
