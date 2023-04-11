package niffler.test;

import niffler.data.dao.PostgresHibernateUsersRepository;
import niffler.data.entity.UsersEntity;
import org.junit.jupiter.api.*;

import static niffler.model.Currency.*;

public class UserFriendsTest extends BaseTest {
    static PostgresHibernateUsersRepository usersRepository = new PostgresHibernateUsersRepository();

    static UsersEntity user1 = UsersEntity.builder()
            .username("user1")
            .currency(EUR.toString())
            .firstname("User1Firstname")
            .surname("User1Surname")
            .build();
    static UsersEntity user2 = UsersEntity.builder()
            .username("user2")
            .currency(KZT.toString())
            .firstname("User2Firstname")
            .surname("User2Surname")
            .build();
    static UsersEntity user3 = UsersEntity.builder()
            .username("user3")
            .currency(USD.toString())
            .firstname("User3Firstname")
            .surname("User3Surname")
            .build();

    @BeforeAll
    static void setUp() {
        usersRepository.addUser(user1);
        usersRepository.addUser(user2);
        usersRepository.addUser(user3);
    }

    @Test
    void friendsTest() {
        user1.addFriends(user2);
        user1.addFriends(user3);
        usersRepository.updateUser(user1);

        UsersEntity user1Updated = usersRepository.getByUsername(user1.getUsername());
        Assertions.assertEquals(2, user1Updated.getFriends().size());
    }

    @AfterAll
    static void clear() {
        usersRepository.removeUser(user1);
        usersRepository.removeUser(user2);
        usersRepository.removeUser(user3);
    }
}
