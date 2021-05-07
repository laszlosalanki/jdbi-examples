package user;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        Locale l = Locale.getDefault();
        FakeUserCreator fuc = new FakeUserCreator(l);

        List<User> users = jdbi.withExtension(UserDao.class, dao -> {
            dao.createTable();

            for (int i = 0; i < 5; i++) {
                User u = fuc.createFakeUser();
                dao.insertUser(u);
            }

            Long id = 2L;

            if (dao.getUserByID(id).isPresent()) {
                User user1 = dao.getUserByID(id).get();
                System.out.printf("Getting user by id %d%n", id);
                System.out.println(user1);
            }

            if (dao.getUsers().get(4) != null) {
                System.out.printf("Getting the username of %d. user : %s%n", 4, dao.getUsers().get(4).getUsername());
            }

            System.out.printf("Deleting user by id %d%n", id);
            dao.deleteUser(dao.getUserByID(id).get());

            return dao.getUsers();
        });

        users.forEach(System.out::println);
    }

}
