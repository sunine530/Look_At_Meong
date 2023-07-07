package com.su.look_at_meong.exception.constant;

import com.su.look_at_meong.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {

    ITEM_SOLD_OUT(HttpStatus.BAD_REQUEST, "품절입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
