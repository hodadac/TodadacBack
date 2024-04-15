package com.example.todadakproject.service.auth;


import com.example.todadakproject.component.JwtTokenProvider;
import com.example.todadakproject.controller.auth.dto.AuthRequestDto;
import com.example.todadakproject.controller.auth.dto.AuthResponseDto;
import com.example.todadakproject.controller.member.CustomUserDetails;
import com.example.todadakproject.controller.member.dto.MemberRequestDto;
import com.example.todadakproject.repository.auth.AuthRepository;
import com.example.todadakproject.repository.auth.entity.Auth;
import com.example.todadakproject.repository.member.MemberRepository;
import com.example.todadakproject.repository.member.entity.Member;
import com.example.todadakproject.repository.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /** 로그인 */
    @Transactional
    public AuthResponseDto login(AuthRequestDto requestDto) {
        // CHECK USERNAME AND PASSWORD
        Member member = this.memberRepository.findByUserId(requestDto.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. username = " + requestDto.getUserId()));
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. username = " + requestDto.getUserId());
        }
        // GENERATE ACCESS_TOKEN AND REFRESH_TOKEN
        String accessToken = this.jwtTokenProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(member), member.getPassword()));
        System.out.println(accessToken);
        String refreshToken = this.jwtTokenProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(member), member.getPassword()));
        System.out.println(refreshToken);

        // CHECK IF AUTH ENTITY EXISTS, THEN UPDATE TOKEN
        if (this.authRepository.existsByMember(member)) {
            member.getAuth().setAccessToken(accessToken);
            member.getAuth().setRefreshToken(refreshToken);
            return new AuthResponseDto(member.getAuth());
        }

        // IF NOT EXISTS AUTH ENTITY, SAVE AUTH ENTITY AND TOKEN
        Auth auth = this.authRepository.save(Auth.builder()
                .member(member)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
        return new AuthResponseDto(auth);
    }

    /** 회원가입 */
    @Transactional
    public String signup(MemberRequestDto requestDto) {
        // SAVE USER ENTITY
        if(memberRepository.findByUserId(requestDto.getUserId()).isEmpty()){
            requestDto.setRole(Role.ROLE_USER);
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            this.memberRepository.save(requestDto.toEntity());
            return "OK";
        }
        return "Fail";
    }

    /** Token 갱신 */
    @Transactional
    public String refreshToken(String refreshToken) {
        // CHECK IF REFRESH_TOKEN EXPIRATION AVAILABLE, UPDATE ACCESS_TOKEN AND RETURN
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(auth.getMember()), auth.getMember().getPassword()));
            auth.setAccessToken(newAccessToken);
            return newAccessToken;
        }

        // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
        // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
        return null;
    }
}
