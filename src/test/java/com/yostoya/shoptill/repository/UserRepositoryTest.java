package com.yostoya.shoptill.repository;

import com.yostoya.shoptill.domain.Role;
import com.yostoya.shoptill.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User newUser;

    private void init() {
        newUser = new User(null, "f", "l", "test@gmail.com", "p", Role.USER,
                LocalDateTime.now());
        userRepository.save(newUser);

    }

    @Test
    void existsByEmail_true() {

        init();

        final boolean actual = userRepository.existsByEmail(newUser.getEmail());
        assertThat(actual).isTrue();
    }

    @Test
    void existsByEmail_false() {
        assertThat(userRepository.existsByEmail("invalid@email.com")).isFalse();
    }

    @Test
    void findByEmail() {

        init();

        final Optional<User> user = userRepository.findByEmail(newUser.getEmail());

        assertThat(user).isNotEmpty();
        assertThat(user.get().getEmail()).isEqualTo(newUser.getEmail());
        assertThat(user.get().getFirstName()).isEqualTo(newUser.getFirstName());
        assertThat(user.get().getLastName()).isEqualTo(newUser.getLastName());
    }

    @Test
    void findByEmail_empty() {
        final Optional<User> user = userRepository.findByEmail("invalid@email.com");
        assertThat(user).isEmpty();
    }
}