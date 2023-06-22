package com.su.look_at_meong.service.oauth;

import com.su.look_at_meong.model.member.dto.TokenDto;
import com.su.look_at_meong.model.member.entity.Member;
import com.su.look_at_meong.repository.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${oauth2.kakao.restApiKey}")
    private String REST_API_KEY;

    @Value("${oauth2.kakao.redirectUrl}")
    private String REDIRECT_URL;

    private final RestTemplate restTemplate;

    private final MemberRepository memberRepository;

    public String getKaKaoAccessToken(String code) {

        String reqURL = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", REST_API_KEY);
        params.add("redirect_url", REDIRECT_URL);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        System.out.println(kakaoTokenRequest);

        ResponseEntity<String> response = restTemplate.exchange(
            reqURL,
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        String tokenJson = response.getBody();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(tokenJson);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonObject.get("access_token").toString();
    }

    public HashMap<String, String> getKakaoInfo(String accessToken) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            reqURL,
            HttpMethod.POST,
            kakaoUserRequest,
            String.class
        );

        System.out.println("카카오 서버에서 정상적으로 데이터를 수신했습니다.");

        String userInfoJson = response.getBody();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(userInfoJson);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, String> userInfo = new HashMap<>();
        JSONObject properties = (JSONObject) jsonObject.get("properties");
        JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");

        userInfo.put("nickname", properties.get("nickname").toString());
        userInfo.put("email", kakao_account.get("email").toString());

        return userInfo;
    }

    public String getKakaoToken(Map<String, String> userInfo) {

        String email = userInfo.get("email");
        String nickname = userInfo.get("nickname");

        if (!isExist(email)) {
            memberRepository.save(Member.builder()
                .name(nickname)
                .email(email)
                .build());
        }

        return null;
    }

    private boolean isExist(String email) {
        return memberRepository.existsByEmail(email);
    }
}