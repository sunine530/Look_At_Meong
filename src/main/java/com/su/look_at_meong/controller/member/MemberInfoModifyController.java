package com.su.look_at_meong.controller.member;

import com.su.look_at_meong.model.member.dto.ModifyMemberDto;
import com.su.look_at_meong.model.member.entity.ModifyMember;
import com.su.look_at_meong.service.member.MemberService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/modify")
public class MemberInfoModifyController {

    private final MemberService memberService;

    @PutMapping("/member")
    public ResponseEntity<ModifyMemberDto> modify(Principal principal
        , @RequestBody ModifyMember modifyMember) {

        return ResponseEntity.ok(memberService.modifyMemberInfo(principal.getName(),modifyMember));
    }
}
