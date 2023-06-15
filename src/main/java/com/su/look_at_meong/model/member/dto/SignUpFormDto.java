package com.su.look_at_meong.model.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NotBlank
@Builder
public class SignUpFormDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotBlank
    private String name;
    @NotEmpty
    private String phone;
}
