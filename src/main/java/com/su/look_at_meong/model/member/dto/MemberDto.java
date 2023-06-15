package com.su.look_at_meong.model.member.dto;

import com.su.look_at_meong.model.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String email;
    private String name;

    public static MemberDto from(Member member) {
        return new MemberDto(member.getEmail(), member.getName());
    }
}
