package tn.arabsoft.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notifications")
public class Notification {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String targetMatricule;
	    private String message;
	    private String type = "NEW_CONGE";
	    @Column(name = "is_read")
	    private boolean isRead = false;

	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdAt = new Date();

	    public Notification() {}

	    public Notification(String targetMatricule, String message) {
	        this.targetMatricule = targetMatricule;
	        this.message = message;
	    }

	    // Getters and Setters
	    public Long getId() { return id; }

	    public String getTargetMatricule() { return targetMatricule; }
	    public void setTargetMatricule(String targetMatricule) { this.targetMatricule = targetMatricule; }

	    public String getMessage() { return message; }
	    public void setMessage(String message) { this.message = message; }

	    public String getType() { return type; }
	    public void setType(String type) { this.type = type; }

	    public boolean isRead() { return isRead; }
	    public void setRead(boolean isRead) { this.isRead = isRead; }

	    public Date getCreatedAt() { return createdAt; }
	    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
