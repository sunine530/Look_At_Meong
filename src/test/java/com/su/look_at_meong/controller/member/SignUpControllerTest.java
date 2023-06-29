package com.su.look_at_meong.controller.member;


import static com.su.look_at_meong.model.member.constant.Role.MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.su.look_at_meong.model.member.dto.MemberDto;
import com.su.look_at_meong.model.member.dto.SignUpFormDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {

    @InjectMocks
    private SignUpController signUpController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();
    }

    @DisplayName("회원 가입 성공")
    @Test
    void signUp_Success() throws Exception {
        //given
        SignUpFormDto request = signUpFormDto();
        Member response = member();

        given(memberService.signUp(any()))
            .willReturn(MemberDto.from(Member.builder()
                .email("test@test.com")
                .name("홍길동")
                .build()));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signup/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then

        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("email", response.getEmail()).exists())
            .andExpect(jsonPath("name", response.getName()).exists())
            .andReturn();
    }

    private SignUpFormDto signUpFormDto() {
        return SignUpFormDto.builder()
            .email("test@test.com")
            .password("1234")
            .name("홍길동")
            .phone("010-1111-1111")
            .build();
    }

    private Member member() {
        return Member.builder()
            .email("test@test.com")
            .password("1234")
            .name("홍길동")
            .phone("010-1111-1111")
            .role(MEMBER)
            .build();
    }
}