package com.example.todadakproject.repository.member.entity;

import com.example.todadakproject.repository.auth.entity.Auth;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Auth auth;

}
