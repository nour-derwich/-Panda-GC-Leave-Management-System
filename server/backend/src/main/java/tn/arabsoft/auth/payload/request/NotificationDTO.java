package tn.arabsoft.auth.payload.request;

import java.util.Date;

public class NotificationDTO {
    private String message;
    private Long id;
    private boolean isRead;
    private Date createdAt;

    public NotificationDTO() {}

    public NotificationDTO(Long id, String message, boolean isRead, Date createdAt) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
}