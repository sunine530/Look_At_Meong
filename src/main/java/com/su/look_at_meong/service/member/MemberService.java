package com.su.look_at_meong.service.member;

import com.su.look_at_meong.constatnt.Role;
import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberDto signUp(SignUpFormDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.info("가입 실패! 중복된 이메일입니다.");
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        return MemberDto.from(
            memberRepository.save(Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .role(Role.MEMBER)
                .build()));
    }
}
