package tn.arabsoft.auth.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.arabsoft.auth.entity.ChatMessage;

public interface ChatMessageRepository  extends JpaRepository<ChatMessage, Long> {
	 List<ChatMessage> findBySenderAndReceiverOrReceiverAndSender(
		        String sender1, String receiver1, String sender2, String receiver2
		    );
	 
	 @Query("SELECT m FROM ChatMessage m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)")
	 List<ChatMessage> findChatBetween(@Param("user1") String user1, @Param("user2") String user2);

	 
	 
	 List<ChatMessage> findByReceiverAndRead(String receiver, boolean read);
	 List<ChatMessage> findBySenderAndReceiverAndRead(String sender, String receiver, boolean read);
	 @Modifying
	 @Query("DELETE FROM ChatMessage m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)")
	 void deleteChatBetween(@Param("user1") String user1, @Param("user2") String user2);
}
