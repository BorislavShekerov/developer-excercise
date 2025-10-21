package com.yostoya.shoptill.web;

import com.yostoya.shoptill.domain.HttpResponse;
import com.yostoya.shoptill.domain.dto.*;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.security.TokenProvider;
import com.yostoya.shoptill.security.UserPrincipal;
import com.yostoya.shoptill.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody @Valid final RegisterDto registerDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        final UserDto registered = userService.register(registerDto);
        final URI location = uriComponentsBuilder.path("/users/get/{id}").buildAndExpand(registered.id()).toUri();

        return ResponseEntity
                .created(location)
                .body((new HttpResponse(HttpStatus.CREATED, "User registered", Map.of("user", registered))));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginDto loginDTO) {

        final Authentication authentication = authenticate(loginDTO.email(), loginDTO.password());
        var user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        return ok((new HttpResponse(HttpStatus.OK, "Login successful", Map.of(
                "access_token", tokenProvider.createAccessToken(new UserPrincipal(user)),
                "refresh_token", tokenProvider.createRefreshToken(new UserPrincipal(user))))));
    }

    @PostMapping("/checkout")
    public ResponseEntity<HttpResponse> checkout(@RequestBody @Valid NewOrderDto newOrder,
                                                 Authentication authentication) {
        final OrderDto order = userService.tillCheckout(newOrder);
        order.setUser(((UserDto) authentication.getPrincipal()).id());
        return ok(new HttpResponse(HttpStatus.OK, "Checkout completed", Map.of("order", order)));
    }

    private Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(unauthenticated(email, password));
    }
}
