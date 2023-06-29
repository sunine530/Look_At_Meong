package com.su.look_at_meong.service.member;

import static com.su.look_at_meong.model.member.constant.Role.MEMBER;
import static com.su.look_at_meong.exception.constant.MemberErrorCode.NOT_FOUND_MEMBER;
import static com.su.look_at_meong.exception.constant.MemberErrorCode.PASSWORD_ENTERED_IS_INCORRECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.su.look_at_meong.exception.RestApiException;
import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.model.member.entity.ModifyMember;
import com.su.look_at_meong.repository.MemberRepository;
import java.util.Optional;
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

    @DisplayName("회원 정보 수정")
    @Test
    void MODIFY_MEMBER_INFO() {
        //given
        Member response = member();

        ModifyMember request = ModifyMember.builder()
            .currentPassword("1234")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(response));
        given(memberRepository.save(any())).willReturn(response);

        //when
        var result = memberService.modifyMemberInfo("test@test.com", request);

        //then
        assertEquals("김나나", result.getName());
        assertEquals("010-2222-1111", result.getPhone());
    }

    @DisplayName("회원 정보 수정 - 등록된 사용자 없음")
    @Test
    void MODIFY_MEMBER_INFO_FAIL() {
        //given
        ModifyMember request = ModifyMember.builder()
            .currentPassword("1234")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberRepository.findByEmail(any())).willThrow(new RestApiException(NOT_FOUND_MEMBER));

        //when
        RestApiException exception = assertThrows(RestApiException.class,
            () -> memberService.modifyMemberInfo("test1@test.com", request));

        //then
        assertEquals(NOT_FOUND_MEMBER, exception.getErrorCode());
    }

    @DisplayName("회원 정보 수정 - 비밀번호 불일치")
    @Test
    void MODIFY_MEMBER_INFO_PASSWORD_FAIL() {
        //given
        Member response = member();
        ModifyMember request = ModifyMember.builder()
            .currentPassword("1111")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(response));

        //when
        RestApiException exception = assertThrows(RestApiException.class,
            () -> memberService.modifyMemberInfo("test@test.com", request));

        //then
        assertEquals(PASSWORD_ENTERED_IS_INCORRECT, exception.getErrorCode());
    }

    private Member member() {
        return Member.builder()
            .email("test@test.com")
            .password(passwordEncoder.encode("1234"))
            .name("홍길동")
            .phone("010-1111-1111")
            .role(MEMBER)
            .build();
    }
}