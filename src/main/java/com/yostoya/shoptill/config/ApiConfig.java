package com.yostoya.shoptill.config;

import com.yostoya.shoptill.domain.Role;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.repository.UserRepository;
import com.yostoya.shoptill.security.TokenProvider;
import com.yostoya.shoptill.security.UserPrincipal;
import com.yostoya.shoptill.service.facade.TillFacade;
import com.yostoya.shoptill.service.facade.TillFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ApiConfig {

    private final UserRepository userRepository;


    @Bean
    public TillFacade tillFacade() {
        return new TillFacadeImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(TokenProvider tokenProvider) {

        return args -> {

            final User admin = new User(
                    null, "root",
                    "admin", "admin@gmail.com",
                    passwordEncoder().encode("123"),
                    Role.ADMIN, LocalDateTime.now()
            );

            userRepository.save(admin);

            final String accessToken = tokenProvider.createAccessToken(new UserPrincipal(admin));
            System.out.println("ADMIN ACCESS TOKEN: " + System.lineSeparator() + accessToken);
        };
    }
}
