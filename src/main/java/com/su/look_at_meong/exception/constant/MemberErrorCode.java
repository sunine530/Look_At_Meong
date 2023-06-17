package com.su.look_at_meong.exception.constant;

import com.su.look_at_meong.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "일치하는 회원을 찾을 수 없습니다."),
    ALREADY_RESISTER_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
