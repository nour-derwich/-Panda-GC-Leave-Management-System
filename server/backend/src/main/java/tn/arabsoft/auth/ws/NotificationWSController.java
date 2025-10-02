package tn.arabsoft.auth.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import tn.arabsoft.auth.entity.Notification;
import tn.arabsoft.auth.payload.request.*;
@Controller
public class NotificationWSController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification) {
        System.out.println("📡 Sending WebSocket to " + notification.getTargetMatricule() + " with message: " + notification.getMessage());
        messagingTemplate.convertAndSend(
            "/topic/notifications/" + notification.getTargetMatricule(),
            new NotificationDTO(notification.getId(), notification.getMessage(), notification.isRead(), notification.getCreatedAt())
        );
    }
    
}
