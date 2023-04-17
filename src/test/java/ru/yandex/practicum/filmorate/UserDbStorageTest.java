package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDbStorageTest {
    private final UserDbStorage storage;
    private User user1;
    private User user2;

    @BeforeEach
    public void beforeEach() {
        user1 = new User(
                1,
                "test@email.com",
                "Test",
                "TestUser",
                LocalDate.of(1982, Month.APRIL, 17)
        );
        user2 = new User(
                2,
                "test2@email.com",
                "Test2",
                "TestUse2",
                LocalDate.of(1961, Month.APRIL, 12)
        );
        storage.save(user1);
        storage.save(user2);
    }

    @Test
    public void shouldSaveUser() {
        User user3 = new User(
                null,
                "test3@email.com",
                "Test3",
                "TestUse3",
                LocalDate.of(2008, Month.AUGUST, 10)
        );
        storage.save(user3);
        user3.setId(3);
        User user = storage.findOne(3);

        assertThat(user.getId()).isEqualTo(user3.getId());
        assertThat(user.getName()).isEqualTo(user3.getName());
        assertThat(user.getEmail()).isEqualTo(user3.getEmail());
        assertThat(user.getLogin()).isEqualTo(user3.getLogin());
        assertThat(user.getBirthday()).isEqualTo(user3.getBirthday());
    }

    @Test
    public void shouldReturnUserById() {
        assertThat(storage.findOne(1)).isEqualTo(user1);
    }

    @Test
    public void shouldReturnAllUsers() {
        Collection<User> users = storage.findAll();

        assertThat(users.contains(user1)).isTrue();
        assertThat(users).hasSize(2);
    }

    @Test
    public void shouldRemoveUser() {
        storage.remove(user1);

        assertThat(storage.findAll()).hasSize(1);
        assertThat(storage.findOne(2)).isEqualTo(user2);
    }

    @Test
    public void shouldReturnNullUser() {
        storage.remove(user1);
        storage.remove(user2);

        assertThat(storage.findAll()).hasSize(0);
    }

    @Test
    public void shouldUpdateUser() {
        user1.setName("qwerty");
        user1.setLogin("login");
        user1.setBirthday(LocalDate.of(2003, Month.MARCH, 11));
        user1.setEmail("new@email.ru");
        storage.update(user1);
        User user = storage.findOne(1);

        assertThat(user.getName()).isEqualTo(user1.getName());
        assertThat(user.getLogin()).isEqualTo(user1.getLogin());
        assertThat(user.getBirthday()).isEqualTo(user1.getBirthday());
        assertThat(user.getEmail()).isEqualTo(user1.getEmail());
    }
}
