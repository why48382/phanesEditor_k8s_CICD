package org.example.coding_convention.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "로그인 요청 DTO")
    public static class LoginRequest {
        @Schema(description = "이메일", example = "admin@example.com")
        private String email;

        @Schema(description = "비밀번호", example = "1234")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "로그인 응답 DTO")
    public static class LoginResponse {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String accessToken;

        @Schema(description = "리프레시 토큰 (현재는 null)", example = "null")
        private String refreshToken;
    }
}
