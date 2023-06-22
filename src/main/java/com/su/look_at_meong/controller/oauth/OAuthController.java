package com.su.look_at_meong.controller.oauth;

import com.su.look_at_meong.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam(name = "code") String code) {

        log.info("###Code : {}###", code);
        return ResponseEntity.ok(oAuthService.kakaoLogin(code));
    }
}
