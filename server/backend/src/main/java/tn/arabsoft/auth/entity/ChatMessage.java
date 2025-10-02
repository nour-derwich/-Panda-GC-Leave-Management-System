package tn.arabsoft.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class ChatMessage {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String sender;      // matriculeP
	    private String receiver;    // matriculeP
	    private String content;
	    @Column(name = "is_read", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	    private boolean read;
	 
	    
	    private LocalDateTime timestamp;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		public String getReceiver() {
			return receiver;
		}
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
		public boolean isRead() {
		    return read;
		}

		public void setRead(boolean read) {
		    this.read = read;
		}
	
	    
	    
	
}
