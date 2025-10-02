package tn.arabsoft.auth.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.arabsoft.auth.entity.ChatMessage;
import tn.arabsoft.auth.payload.request.ChatMessageDTO;
import tn.arabsoft.auth.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatRestController {
	 @Autowired
	    private ChatMessageRepository messageRepository; 
	
	 @GetMapping("/history")
	public List<ChatMessageDTO> getChatHistory(@RequestParam String user1, @RequestParam String user2) {
	    List<ChatMessage> messages = messageRepository
	    		.findChatBetween(user1, user2);

	    return messages.stream()
	            .sorted(Comparator.comparing(ChatMessage::getTimestamp))
	            .map(m -> new ChatMessageDTO(m.getSender(), m.getReceiver(), m.getContent(), m.getTimestamp()))
	            .collect(Collectors.toList());
	}
	
	@PostMapping("/send")
	public ChatMessage sendTestMessage(@RequestBody ChatMessageDTO dto) {
	    ChatMessage msg = new ChatMessage();
	    msg.setSender(dto.getSender());
	    msg.setReceiver(dto.getReceiver());
	    msg.setContent(dto.getContent());
	    msg.setTimestamp(LocalDateTime.now());
	    
	   
	    
	    return messageRepository.save(msg);
	}
	@GetMapping("/unread-count")
	public Map<String, Long> getUnreadMessages(@RequestParam String receiver) {
	    List<ChatMessage> unread = messageRepository.findByReceiverAndRead(receiver, false);
	    return unread.stream()
	        .collect(Collectors.groupingBy(ChatMessage::getSender, Collectors.counting()));
	}
	
	@PostMapping("/mark-read")
	public void markMessagesAsRead(@RequestBody Map<String, String> payload) {
	    String sender = payload.get("sender");
	    String receiver = payload.get("receiver");

	    List<ChatMessage> messages = messageRepository.findBySenderAndReceiverAndRead(sender, receiver, false);
	    for (ChatMessage msg : messages) {
	        msg.setRead(true);
	    }
	    messageRepository.saveAll(messages);
	}
	
	@DeleteMapping("/clear-chat")
	public void clearChat(@RequestParam String user1, @RequestParam String user2) {
	    messageRepository.deleteChatBetween(user1, user2);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteMessage(@PathVariable Long id) {
	    messageRepository.deleteById(id);
	}



}
