package com.yostoya.shoptill.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;


@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class HttpResponse {

    protected String timestamp;

    protected int statusCode;

    protected HttpStatus status;

    protected String message;

    protected Map<?, ?> data;


    public HttpResponse(HttpStatus status, String message, Map<?, ?> data) {
        this.timestamp = LocalDateTime.now().toString();
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
    }
}
