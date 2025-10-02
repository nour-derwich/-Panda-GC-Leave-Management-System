package tn.arabsoft.auth.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.arabsoft.auth.entity.Personnel;
import tn.arabsoft.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // login avec matriculeP ou utilisation depuis le token (nom)
	    return userRepository.findByMatriculeP(username)
	            .or(() -> userRepository.findByNom(username)) // essaie nom si matriculeP échoue
	            .map(UserDetailsImpl::build)
	            .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
	}

}
