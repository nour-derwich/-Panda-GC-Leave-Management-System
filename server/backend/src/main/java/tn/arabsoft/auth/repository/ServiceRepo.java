package tn.arabsoft.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.arabsoft.auth.entity.Service;

public interface ServiceRepo extends JpaRepository<Service, Long> {
	  
}
