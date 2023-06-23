package com.su.look_at_meong.model.member.dto;

import com.su.look_at_meong.model.member.entity.Member;
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
public class ModifyMemberDto {
    private String name;
    private String phone;

    public ModifyMemberDto from(Member member) {
        return ModifyMemberDto.builder()
            .name(member.getName())
            .phone(member.getPhone())
            .build();
    }
}
