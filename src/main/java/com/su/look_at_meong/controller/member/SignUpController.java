package com.su.look_at_meong.controller.member;

import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<MemberDto> signUp(@RequestBody SignUpFormDto request) {
        return ResponseEntity.ok(memberService.signUp(request));
    }
}
