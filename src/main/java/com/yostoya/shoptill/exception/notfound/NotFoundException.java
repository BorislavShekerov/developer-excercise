package com.yostoya.shoptill.exception.notfound;

import com.yostoya.shoptill.domain.HttpResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpResponse response;

    public NotFoundException(String message) {
        super(message);
        response = generate(message);
    }

    public NotFoundException(String entity, String field, String value) {
        String message = String.format(
                "%s with field {%s} and value: {%s} not found!",
                entity, field, value
        );
        response = generate(message);
    }

    private HttpResponse generate(String message) {
        return HttpResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message(message)
                .build();
    }
}
