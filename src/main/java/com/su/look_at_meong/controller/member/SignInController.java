package com.su.look_at_meong.controller.member;

import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignInDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.dto.TokenDto;
import com.su.look_at_meong.service.member.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(memberService.signIn(signInDto));
    }
}
