package tn.arabsoft.auth.ws;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import tn.arabsoft.auth.entity.ChatMessage;
import tn.arabsoft.auth.repository.ChatMessageRepository;

@Controller
public class ChatWSController {
	
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository messageRepository;
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);

        messagingTemplate.convertAndSend(
            "/topic/chat/" + message.getReceiver(), // Envoi vers le récepteur
            message
        );
    }

}
