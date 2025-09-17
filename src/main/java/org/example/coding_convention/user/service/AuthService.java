package org.example.coding_convention.user.service;

import lombok.RequiredArgsConstructor;
import org.example.coding_convention.user.model.AuthDto;
import org.example.coding_convention.user.model.UserDto;
import org.example.coding_convention.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // 1. 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Principal (UserDto.AuthUser) 꺼내기
        UserDto.AuthUser user =
                (UserDto.AuthUser) authentication.getPrincipal();

        // 3. 기존 JwtUtil 이용해서 토큰 발급
        String token = JwtUtil.generateToken(user.getEmail(), user.getIdx(), user.getNickname());

        // 4. 응답 반환 (Refresh Token은 아직 없음)
        return new AuthDto.LoginResponse(token, null);
    }
}
