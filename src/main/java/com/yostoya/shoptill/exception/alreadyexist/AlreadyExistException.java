package com.yostoya.shoptill.exception.alreadyexist;

import com.yostoya.shoptill.domain.HttpResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.function.LongConsumer;

@Getter
public class AlreadyExistException extends RuntimeException {

    private final HttpResponse response;

    public AlreadyExistException(String message) {
        super(message);
        response = generate(message);
    }

    public AlreadyExistException(String entity, String field, String value) {
        String message = String.format(
                "%s with field {%s} and value: {%s} already exist!",
                entity, field, value
        );
        response = generate(message);
    }

    private HttpResponse generate(String message) {
        return HttpResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();
    }
}
