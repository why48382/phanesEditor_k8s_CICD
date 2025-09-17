package org.example.coding_convention.config;

import lombok.RequiredArgsConstructor;

import org.example.coding_convention.config.filter.JwtAuthFilter;
import org.example.coding_convention.config.filter.LoginFilter;
import org.example.coding_convention.config.oauth.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        //configuration.setAllowedOrigins(List.of("http://localhost:5175") => 불편해서 전체경로 허용(개별용)
        configuration.setAllowedOrigins(
                List.of("https://www.gomorebi.kro.kr")
        );
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 적용
        return source;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(configuration.getAuthenticationManager());
        loginFilter.setFilterProcessesUrl("/api/login"); // JWT 로그인 전용 URL

        http.oauth2Login(config -> {
                    config.userInfoEndpoint(
                            endpoint ->
                                    endpoint.userService(oAuth2UserService)
                    );
                    config.successHandler(oAuth2AuthenticationSuccessHandler);
                }
        );

        http.authorizeHttpRequests(
                (auth) -> auth
                        // Swagger 관련 리소스는 항상 허용
                        .requestMatchers(
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"
                        ).permitAll()

                        // 로그인/회원가입/소셜 로그인은 허용
                        .requestMatchers("/login", "/auth/**", "/user/signup", "/oauth2/**", "/login/oauth2/**", "/user/verify").permitAll()

                        // 프로젝트 검색과 상세조회는 허용
                        .requestMatchers("/project/read", "/project/search", "/file/read/**").permitAll()

                        .requestMatchers("/test", "/health").permitAll()

                        // 테스트 API → USER 권한 필요
                        .requestMatchers("/test/*").hasRole("USER")
                        // 이메일 인증
                        .requestMatchers("/user/verify").permitAll()

                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().permitAll()
        );

        http.cors(cors ->
                cors.configurationSource(corsConfigurationSource()));

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new LoginFilter(configuration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

