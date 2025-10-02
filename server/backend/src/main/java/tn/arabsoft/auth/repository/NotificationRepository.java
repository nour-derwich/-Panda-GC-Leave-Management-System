package tn.arabsoft.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.arabsoft.auth.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByTargetMatriculeAndIsReadFalse(String targetMatricule);
	
	List<Notification> findByTargetMatricule(String matricule);
}
