package com.su.look_at_meong.model.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyMember {

    private String currentPassword;
    private String updatePassword;
    private String name;
    private String phone;
}
