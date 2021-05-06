package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {

    @SqlUpdate("""
        CREATE TABLE users(
            id IDENTITY NOT NULL PRIMARY KEY,
            username VARCHAR,
            password VARCHAR,
            name VARCHAR,
            email VARCHAR,
            gender ENUM,
            birthDate DATE,
            enable BOOLEAN    
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO users (username, password, name, email, gender, birthDate, enable) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enable)")
    @GetGeneratedKeys
    Long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> getUserByID(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    Optional<User> getUserByUsername(@Bind("username") String username);

    //TODO
    @SqlUpdate()
    void deleteUser(User user);

    @SqlQuery("SELECT * FROM users ORDER BY username")
    List<User> getUsers();

}
