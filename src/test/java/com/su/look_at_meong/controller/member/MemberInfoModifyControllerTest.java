package com.su.look_at_meong.controller.member;

import static com.su.look_at_meong.exception.constant.MemberErrorCode.NOT_FOUND_MEMBER;
import static com.su.look_at_meong.exception.constant.MemberErrorCode.PASSWORD_ENTERED_IS_INCORRECT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.su.look_at_meong.exception.RestApiException;
import com.su.look_at_meong.model.member.dto.ModifyMemberDto;
import com.su.look_at_meong.model.member.entity.ModifyMember;
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
class MemberInfoModifyControllerTest {

    @InjectMocks
    private MemberInfoModifyController memberInfoModifyController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberInfoModifyController).build();
    }

    @DisplayName("회원 정보 수정")
    @Test
    void Modify_Member_Info() throws Exception {
        //given
        ModifyMember request = ModifyMember.builder()
            .currentPassword("1234")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        ModifyMemberDto response = ModifyMemberDto.builder()
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberService.modifyMemberInfo(any(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.put("/modify/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("김나나"))
            .andExpect(jsonPath("$.phone").value("010-2222-1111"))
            .andReturn();
    }

    @DisplayName("회원 정보 수정 실패 - 사용자 없음")
    @Test
    void Modify_Member_Info_Fail() throws Exception {
        //given
        ModifyMember request = ModifyMember.builder()
            .currentPassword("1234")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberService.modifyMemberInfo(any(), any())).willThrow(new RestApiException(NOT_FOUND_MEMBER));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.put("/modify/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.errorCode").value("NOT_FOUND_MEMBER"))
            .andExpect(jsonPath("$.message").value("일치하는 회원을 찾을 수 없습니다."))
            .andReturn();
    }

    @DisplayName("회원 정보 수정 실패 - 비밀번호 다름")
    @Test
    void Modify_Member_Info_Fail_Password() throws Exception {
        //given
        ModifyMember request = ModifyMember.builder()
            .currentPassword("1234")
            .updatePassword("")
            .name("김나나")
            .phone("010-2222-1111")
            .build();

        given(memberService.modifyMemberInfo(any(), any())).willThrow(new RestApiException(PASSWORD_ENTERED_IS_INCORRECT));

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.put("/modify/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.errorCode").value("PASSWORD_ENTERED_IS_INCORRECT"))
            .andExpect(jsonPath("$.message").value("입력하신 비밀번호가 올바르지 않습니다."))
            .andReturn();
    }
}