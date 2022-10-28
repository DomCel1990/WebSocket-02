package com.example.websocket02.controller;

import com.example.websocket02.entities.ClientMessageDTO;
import com.example.websocket02.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Notification {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/broadcast-message",messageDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @MessageMapping("/client-message")
    @SendTo("/broadcast-message")
    public ClientMessageDTO sendFromWebSocket(ClientMessageDTO clientMessageDTO){
        System.out.println("Arrived somting on /app/hello - " + clientMessageDTO.toString());
        return new ClientMessageDTO(clientMessageDTO.getClientName(),clientMessageDTO.getClientAlert(),clientMessageDTO.getClientMsg());
    }
}
