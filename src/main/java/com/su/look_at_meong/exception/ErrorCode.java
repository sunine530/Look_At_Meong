package com.su.look_at_meong.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();

    HttpStatus getHttpStatus();

    String getMessage();
}
