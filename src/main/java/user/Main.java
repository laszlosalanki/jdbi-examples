package user;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        var faker = new Faker(new Locale("hu"));
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        User user1 = User.builder()
                .username(faker.name().username())
                .password(DigestUtils.md5Hex(faker.internet().password()))
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(User.class.getEnumConstants()))
                .birthDate(faker.date().birthday())
                .enabled(faker.bool().bool())
                .build();
    }

}
