package niffler.test;


import niffler.jupiter.User;
import niffler.model.UserModel;
import org.junit.jupiter.api.Test;
import niffler.jupiter.UserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.User.UserType.ADMIN;
import static niffler.jupiter.User.UserType.COMMON;


@ExtendWith(UserExtension.class)
public class NifflerLoginTest {

    @Test
    void test1(@User(userType = ADMIN) UserModel user) {
        System.out.println("#### Test 1 " + user.getUsername());
    }

    @Test
    void test2(@User(userType = ADMIN) UserModel userFirst,
               @User(userType = COMMON) UserModel userSecond) {
        System.out.println("#### Test 4 " + userFirst.getUsername());
        System.out.println("#### Test 4 " + userSecond.getUsername());
    }

    @Test
    void test3(@User UserModel user) {
        System.out.println("#### Test 3 " + user.getUsername());
    }

    @Test
    void test4(@User UserModel user) {
        System.out.println("#### Test 4 " + user.getUsername());
    }

    @Test
    void test5(@User UserModel user) {
        System.out.println("#### Test 5 " + user.getUsername());
    }


}
