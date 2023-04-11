# Tests for niffler app

* User/password: `Kate/pass`
* Spend categories:
    ```postgresql
    insert into categories (category, username) values ('Кафе', 'Kate');
    insert into categories (category, username) values ('Продукты', 'Kate');
    insert into categories (category, username) values ('Обучение', 'Kate');
    insert into categories (category, username) values ('Транспорт', 'Kate');
    ```
* Create a table friends to the niffler-userdata:
    ```postgresql
    CREATE TABLE friends
    (
    user_id UUID NOT NULL,
    friend_id UUID NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
    );
  ```