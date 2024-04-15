package com.example.todadakproject.controller.member.dto;

import com.example.todadakproject.repository.member.entity.Member;
import com.example.todadakproject.repository.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberRequestDto {
    private String userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private Date birthDate;
    private Role role;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .userId(userId)
                .nickName(nickName)
                .birthDate(birthDate)
                .role(role)
                .build();
    }
}
