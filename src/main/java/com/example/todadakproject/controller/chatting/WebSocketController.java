package com.example.todadakproject.controller.chatting;


import com.example.todadakproject.controller.chatting.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate template;


    @MessageMapping("/chat")
    public void sendMessage(@Payload ChatDto chatDto, SimpMessageHeaderAccessor accessor) {
        System.out.print(chatDto.getChat());
        template.convertAndSend("/sub/chat/" + chatDto.getChannelId(), chatDto);
    }
}
