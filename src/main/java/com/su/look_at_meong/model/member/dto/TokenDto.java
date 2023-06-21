package com.su.look_at_meong.model.member.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TokenDto {

    private String grantType; // Bearer
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}