package niffler.jupiter;


import niffler.model.Currency;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@ExtendWith(UserEntityExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UserEntity {
    String username();

    Currency currency();
}
