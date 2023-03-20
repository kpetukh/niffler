package niffler.data.dao;

import niffler.data.entity.UsersEntity;

public interface UsersDAO extends DAO {
    int addUser(UsersEntity users);

    void updateUser(UsersEntity user);

    void removeUser(UsersEntity user);

    UsersEntity getByUsername(String username);
}
