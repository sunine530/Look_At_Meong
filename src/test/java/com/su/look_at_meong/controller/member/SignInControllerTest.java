package com.su.look_at_meong.controller.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.su.look_at_meong.exception.RestApiException;
import com.su.look_at_meong.exception.constant.MemberErrorCode;
import com.su.look_at_meong.model.member.dto.SignInDto;
import com.su.look_at_meong.model.member.dto.TokenDto;
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
class SignInControllerTest {

    @InjectMocks
    private SignInController signInController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(signInController).build();
    }


    @DisplayName("로그인 성공")
    @Test
    void Login_Success() throws Exception {
        //given
        SignInDto request = signInDto();

        given(memberService.signIn(any()))
            .willReturn(TokenDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build());

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signIn/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    }

    @DisplayName("로그인 실패 - 이메일 입력 오류")
    @Test
    void Login_Fail_Email() throws Exception {
        //given
        SignInDto request = signInDto();

        given(memberService.signIn(any()))
            .willThrow(new RestApiException(MemberErrorCode.NOT_FOUND_MEMBER));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signIn/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.errorCode").value("NOT_FOUND_MEMBER"))
            .andExpect(jsonPath("$.message").value("일치하는 회원을 찾을 수 없습니다."))
            .andReturn();
    }

    @DisplayName("로그인 실패 - 비밀번호 입력 오류")
    @Test
    void Login_Fail_Password() throws Exception {
        //given
        SignInDto request = signInDto();

        given(memberService.signIn(any()))
            .willThrow(new RestApiException(MemberErrorCode.PASSWORD_ENTERED_IS_INCORRECT));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signIn/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.errorCode").value("PASSWORD_ENTERED_IS_INCORRECT"))
            .andExpect(jsonPath("$.message").value("입력하신 비밀번호가 올바르지 않습니다."))
            .andReturn();
    }

    private SignInDto signInDto() {
        return SignInDto.builder()
            .email("test@test.com")
            .password("1234")
            .build();
    }
}