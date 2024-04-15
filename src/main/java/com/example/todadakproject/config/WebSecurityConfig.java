package com.example.todadakproject.config;


import com.example.todadakproject.component.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeRequests((authorizeRequests)->
//                    authorizeRequests
//                            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                            .requestMatchers("/api/v2/admin/**").hasRole("ADMIN")
//                            // 회원가입 및 로그인 관련 모든 요청에 대해 아무나 승인
//                            .requestMatchers("/api/v1/auth/**").permitAll()
//                            .requestMatchers("/api/v2/auth/**").permitAll()
//                            // 중복체크 관련 모든 요청에 대해 아무나 허용
//                            .requestMatchers("/api/v1/user/check/**").permitAll()
//                            .requestMatchers("/api/v2/user/check/**").permitAll()
//                            // 유저정보 관련 모든 요청에 대해 승인된 사용자만 허용
//                            .requestMatchers("/api/v1/user/**").authenticated()
//                            .requestMatchers("/api/v2/user/**").authenticated()
//                            // 첨부파일 관련 GET 요청에 대해 아무나 승인
//                            .requestMatchers(HttpMethod.GET, "/api/v1/attachment/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/api/v2/attachment/**").permitAll()
//                            // 댓글 관련 GET 요청에 대해 아무나 승인
//                            .requestMatchers(HttpMethod.GET, "/api/v1/comment/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/api/v2/comment/**").permitAll()
//                            // 게시글 관련 GET 요청에 대해 아무나 승인
//                            .requestMatchers(HttpMethod.GET, "/api/v1/post/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/api/v2/post/**").permitAll()
//                            // 기타 모든 요청에 대해 승인된 사용자만 허용
//                            .requestMatchers("/api/v1/**").authenticated()
//                            .requestMatchers("/api/v2/**").authenticated()
//                )
                .sessionManagement((sessionManagement)->
                    sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
