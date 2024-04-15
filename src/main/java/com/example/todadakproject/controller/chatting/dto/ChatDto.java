package com.example.todadakproject.controller.chatting.dto;

import lombok.Data;

@Data
public class ChatDto {
    private Integer channelId;
    private String writerName;
    private String chat;
}
