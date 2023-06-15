package com.su.look_at_meong.service.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원 가입")
    @Test
    void signUp() {

        //given
        given(memberRepository.save(any()))
            .willReturn(Member.builder()
                .email("test@test.com")
                .password("1234")
                .name("홍길동")
                .phone("010-1111-1111")
                .build());

        //when
        MemberDto memberDto = memberService.signUp(
            new SignUpFormDto(
                "test2@test.com",
                "1111",
                "김철수",
                "010-2222-2222")
        );
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        // verify
        verify(memberRepository, times(1)).save(captor.capture());

        //then
        assertEquals("test2@test.com", captor.getValue().getEmail());
        assertEquals("test@test.com", memberDto.getEmail());
        assertEquals("홍길동", memberDto.getName());
    }
}