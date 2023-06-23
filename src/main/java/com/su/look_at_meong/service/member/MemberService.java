package com.su.look_at_meong.service.member;

import static com.su.look_at_meong.exception.constant.MemberErrorCode.ALREADY_RESISTER_EMAIL;
import static com.su.look_at_meong.exception.constant.MemberErrorCode.NOT_FOUND_MEMBER;
import static com.su.look_at_meong.exception.constant.MemberErrorCode.PASSWORD_ENTERED_IS_INCORRECT;

import com.su.look_at_meong.config.jwt.JwtProvider;
import com.su.look_at_meong.constatnt.Role;
import com.su.look_at_meong.exception.RestApiException;
import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.ModifyMemberDto;
import com.su.look_at_meong.model.member.dto.SignInDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.dto.TokenDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.model.member.entity.ModifyMember;
import com.su.look_at_meong.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisTemplate redisTemplate;

    public MemberDto signUp(SignUpFormDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.error("가입 실패! 중복된 이메일입니다.");
            throw new RestApiException(ALREADY_RESISTER_EMAIL);
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

    public TokenDto signIn(SignInDto signInDto) {

        Member member = memberRepository.findByEmail(signInDto.getEmail())
            .orElseThrow(
                () -> new RestApiException(NOT_FOUND_MEMBER)
            );

        if (!validationLogin(signInDto.getPassword(), member.getPassword())) {
            throw new RestApiException(PASSWORD_ENTERED_IS_INCORRECT);
        }

        String email = member.getEmail();
        TokenDto tokenDto = jwtProvider.generateTokenDto(email);

        redisTemplate.opsForValue()
            .set("RT:" + email, tokenDto.getRefreshToken(),tokenDto.getAccessTokenExpiresIn(), TimeUnit.MICROSECONDS);

        return tokenDto;
    }

    private boolean validationLogin(String formPassword, String encodingPassword) {
        return passwordEncoder.matches(formPassword, encodingPassword);
    }

    public ModifyMemberDto modifyMemberInfo(String email, ModifyMember modifyMember) {

        var member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new RestApiException(NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(modifyMember.getCurrentPassword(), member.getPassword())) {
            throw new RestApiException(PASSWORD_ENTERED_IS_INCORRECT);
        }

        member.setName(modifyMember.getName());
        member.setPhone(modifyMember.getPhone());

        if (!modifyMember.getUpdatePassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(modifyMember.getUpdatePassword()));
        }

        return new ModifyMemberDto().from(memberRepository.save(member));
    }
}
