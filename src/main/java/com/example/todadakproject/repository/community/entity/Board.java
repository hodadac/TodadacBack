package com.example.todadakproject.repository.community.entity;

import jakarta.persistence.*;
import lombok.*;



@Data // @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
@Entity
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long hit;

    public Board(Long id, String title, String content, Long hit) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hit = hit;
    }

}