package com.example.todadakproject.controller.member.dto;

import com.example.todadakproject.repository.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MemberResponseDto {

    private Long idx;
    private String userId;
    private String email;
    private String name;
    private String nickName;
    private Date birthDate;
    private String role;

    public MemberResponseDto(Member member){
        this.idx=member.getIdx();
        this.userId=member.getUserId();
        this.email=member.getEmail();
        this.birthDate=member.getBirthDate();
        this.nickName=member.getNickName();
        this.name=member.getName();
        this.role=member.getRole().name();
    }
}
