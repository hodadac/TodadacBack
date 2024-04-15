package com.example.todadakproject.repository.auth.entity;

import com.example.todadakproject.repository.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Setter
public class Auth{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Member member;


}