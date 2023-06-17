package com.su.look_at_meong.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    // errors가 없다면 응답으로 내려가지 않도록 함
    @JsonInclude(Include.NON_EMPTY)
    private final List<ValidationError> errors;


    // @Valid를 사용했을 때 에러가 발생한 경우 어느 필드에서 에러가 발생했는지 응답을 위한 ValidationError를 내부 정적 클래스로 추가
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getField())
                .build();
        }
    }
}
