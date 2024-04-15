package com.example.todadakproject.controller.auth;

import com.example.todadakproject.controller.auth.dto.AuthRequestDto;
import com.example.todadakproject.controller.auth.dto.AuthResponseDto;
import com.example.todadakproject.controller.member.dto.MemberRequestDto;
import com.example.todadakproject.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto) {
        System.out.print(requestDto.getPassword());
        AuthResponseDto responseDto = this.authService.login(requestDto);
        System.out.print(responseDto.getAccessToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /** 회원가입 API */
    @PostMapping("/auth/signup")
    public ResponseEntity<?> singUp(@RequestBody MemberRequestDto requestDto) {
        System.out.print(requestDto.getPassword());
        String response = this.authService.signup(requestDto);
        if(response.equals("OK")){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    /** 토큰갱신 API */
    @GetMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("REFRESH_TOKEN") String refreshToken) {
        String newAccessToken = this.authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }

}
