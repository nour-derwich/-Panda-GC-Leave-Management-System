package tn.arabsoft.auth.controler;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.arabsoft.auth.entity.Role;
import tn.arabsoft.auth.entity.Service;
import tn.arabsoft.auth.entity.user_role;
import tn.arabsoft.auth.entity.ERole;
import tn.arabsoft.auth.entity.Personnel;
import tn.arabsoft.auth.payload.request.LoginRequest;
import tn.arabsoft.auth.payload.request.SignupRequest;
import tn.arabsoft.auth.payload.request.UpdateRoleNiveauRequest;
import tn.arabsoft.auth.repository.RoleRepository;
import tn.arabsoft.auth.repository.ServiceRepo;
import tn.arabsoft.auth.repository.UserRepository;
import tn.arabsoft.auth.repository.UserRoleRepo;
import tn.arabsoft.auth.response.JwtResponse;
import tn.arabsoft.auth.response.MessageResponse;
import tn.arabsoft.auth.security.jwt.JwtUtils;
import tn.arabsoft.auth.security.service.UserDetailsImpl;
import tn.arabsoft.auth.security.service.UserDetailsServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class Auth {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsServiceImpl  serv;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;
  @Autowired
  UserRoleRepo userRole;
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  ServiceRepo serviceRepo;
  @GetMapping("/get/{mat}")
  public UserDetails get(@PathVariable String mat){
  	return this.serv.loadUserByUsername(mat);
  }
  
  @GetMapping("/gett/{mat}")
  public Optional<Personnel> gett(@PathVariable String mat){
  	return this.userRepository.findByMatriculeP(mat);
  }
  
  
  
  @GetMapping("/getUsers")
  public List<Personnel> getUsers(){
  	return this.userRepository.findAll();
  }
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getMatriculeP(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    Optional<Personnel> pers = userRepository.findByMatriculeP(loginRequest.getMatriculeP());
    System.out.println(pers);
    return ResponseEntity.ok(new JwtResponse(jwt, 
                         "Bearer", 
                         userDetails.getIdEmploye(), 
                         userDetails.getNomResponsable(), 
                         userDetails.getMatriculeP(),
                         userDetails.getEmail(), 
                          userDetails.getPassword(),userDetails.getNom(),userDetails.getPrenom(),userDetails.getNumTel()
                          ,userDetails.getPoste(),userDetails.getDepartement(),userDetails.getServ(),roles));
  }
 
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	  
	  // Check if matriculeP is provided and exists
	    if (signUpRequest.getMatriculeP() != null && !signUpRequest.getMatriculeP().isEmpty()) {
	        if (userRepository.existsByMatriculeP(signUpRequest.getMatriculeP())) {
	            return ResponseEntity.badRequest().body(new MessageResponse("Error: Matricule already exists!"));
	        }
	    }
      // Existing validation
      if (userRepository.existsByMatriculeP(signUpRequest.getMatriculeP())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
      }

      // Get responsable from matricule
      Personnel responsable = null;
      if (signUpRequest.getResponsableMatricule() != null) {
          responsable = userRepository.findByMatriculeP(signUpRequest.getResponsableMatricule())
              .orElseThrow(() -> new RuntimeException("Responsable not found"));
      }
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
    	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already used!"));
    	}
      
      if (signUpRequest.getPassword().length() < 4) {
    	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Password must be at least 4 characters!"));
    	}
     

      // Create user with responsable relationship
      Personnel user = new Personnel(
    		    signUpRequest.getEmail(),
    		    signUpRequest.getMatriculeP(),
    		    signUpRequest.getNom(),
    		    signUpRequest.getPrenom(),
    		    signUpRequest.getDepartement(),
    		    responsable, // Pass the fetched Personnel object
    		    signUpRequest.getNumTel(),
    		    signUpRequest.getPoste(),
    		    encoder.encode(signUpRequest.getPassword()),
    		    signUpRequest.getServ()
    		);

      // Role handling (existing code)
      Integer intRoles = signUpRequest.getRoles();
      Set<Role> roles = new HashSet<>();
      if(intRoles != null) {
          Role userRole = roleRepository.findById(intRoles)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      } else {
          Role userRole = roleRepository.findById(1)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      }

      // Generate matricule (existing code)
      if (signUpRequest.getMatriculeP() == null || signUpRequest.getMatriculeP().isEmpty()) {
      String matMax = this.userRepository.getMat();
      long number = Long.parseLong(matMax);
      number += 1;
      String output = String.format("%04d", number + 1);
      System.out.println(output);
      user.setMatriculeP(output);
      user.setRoles(roles);
      } else {
    	    user.setMatriculeP(signUpRequest.getMatriculeP()); // Keep user-provided matriculeP
    	}

      userRepository.save(user);

      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  } 
 @GetMapping("/get")
 public List<Personnel> getPersonnel(){
	 return userRepository.findAll();
 }
 @GetMapping("/getService")
 public List<Service> getService(){
	 return serviceRepo.findAll();
 }

 @GetMapping("/getuseRole/{use}")
 public Optional<user_role> getUse(@PathVariable long use){
	 return userRole.getByUser_id(use);
 }
 @PutMapping("/updatePass")
 
 public ResponseEntity<Personnel> updatePass( @RequestBody Personnel Ag) {
 
    Optional<Personnel> AgData = userRepository.findById(Ag.getIdEmploye());
   if (AgData.isPresent()) {
	   Personnel agg = AgData.get();
  agg.setPassword(encoder.encode(Ag.getPassword()));

    return new ResponseEntity<>(userRepository.save(agg), HttpStatus.OK);
   } else {
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   } }
 
 @PutMapping("/setRole")
 
 public ResponseEntity<?> updateRh(@RequestBody UpdateRoleNiveauRequest request) {
     try {
         // 1. Update user_role
         Optional<user_role> userRoleOpt = userRole.getByUser_id(request.getUser_id());
         if (!userRoleOpt.isPresent()) {
             return ResponseEntity.notFound().build();
         }
         
         user_role userRoleEntity = userRoleOpt.get();
         userRoleEntity.setRole_id(request.getRole_id());
         userRole.save(userRoleEntity);

         // 2. Update niveau and responsable
         Optional<Role> newRole = roleRepository.findById((int) request.getRole_id());
         Optional<Personnel> personnelOpt = userRepository.findById((int) request.getUser_id());

         if (newRole.isPresent() && personnelOpt.isPresent()) {
             Personnel personnel = personnelOpt.get();
             
             if (newRole.get().getNomRole().equals(ERole.ROLE_CHEF)) { 
                 // Validate niveau
                 if (request.getNiveau() == null || request.getNiveau() < 1 || request.getNiveau() > 3) {
                     return ResponseEntity.badRequest().body("Niveau doit être entre 1 et 3 !");
                 }
                 personnel.setNiveau(request.getNiveau());

                 // Assign responsable based on niveau
                 if (personnel.getServ() == null) {
                     return ResponseEntity.badRequest().body("L'utilisateur doit appartenir à un service !");
                 }

                 Long serviceId = personnel.getServ().getIdService();
                 Personnel responsable = null;

                 switch (request.getNiveau()) {
                     case 3:
                         // Find niveau 2 chef in the same service
                         responsable = userRepository.findFirstByServIdServiceAndRolesNomRoleAndNiveau(
                             serviceId, ERole.ROLE_CHEF, 2
                         ).orElseThrow(() -> new RuntimeException("Aucun chef niveau 2 trouvé dans ce service"));
                         break;
                         
                     case 2:
                         // Find niveau 1 chef in the same service
                         responsable = userRepository.findFirstByServIdServiceAndRolesNomRoleAndNiveau(
                             serviceId, ERole.ROLE_CHEF, 1
                         ).orElseThrow(() -> new RuntimeException("Aucun chef niveau 1 trouvé dans ce service"));
                         break;

                     case 1:
                         // Niveau 1 has no responsable
                         responsable = null;
                         break;
                 }

                 personnel.setResponsable(responsable);

             } else {
                 // Clear niveau and responsable for non-chefs
                 personnel.setNiveau(null);
                 personnel.setResponsable(null);
             }

             userRepository.save(personnel);
         }

         return ResponseEntity.ok().build();
     } catch (Exception e) {
         return ResponseEntity.internalServerError().body(e.getMessage());
     }
 }
	 
 @PutMapping("/add")
 @Transactional
 public ResponseEntity<Personnel> addPers(@RequestBody Personnel updatedUser) {
     Optional<Personnel> userData = userRepository.findById(updatedUser.getIdEmploye());
     if (userData.isPresent()) {
         Personnel user = userData.get();

         // Update core fields (EXCLUDING responsable)
         user.setEmail(updatedUser.getEmail());
         user.setNom(updatedUser.getNom());
         user.setPrenom(updatedUser.getPrenom());
         user.setNumTel(updatedUser.getNumTel());
         user.setPoste(updatedUser.getPoste());
         user.setDepartement(updatedUser.getDepartement());
         user.setServ(updatedUser.getServ());

         // Handle responsable update using matriculeP
         if (updatedUser.getResponsable() != null) {
             String responsableMatricule = updatedUser.getResponsable().getMatriculeP();
             
             if (responsableMatricule != null && !responsableMatricule.trim().isEmpty()) {
                 Optional<Personnel> responsable = userRepository.findByMatriculeP(responsableMatricule);
                 user.setResponsable(responsable.orElse(null)); // Set fetched entity or null
             } else {
                 user.setResponsable(null); // Clear if matricule is empty
             }
         } else {
             user.setResponsable(null); // Clear if no responsable in request
         }

         userRepository.save(user);
         return ResponseEntity.ok(user);
     } else {
         return ResponseEntity.notFound().build();
     }
 }
 @GetMapping("/getChefsByService/{serviceId}")
 public List<Personnel> getChefsByService(@PathVariable Long serviceId) {
     // Fetch users in the service with CHEF role
     return userRepository.findByServ_IdServiceAndRoles_NomRole(serviceId, ERole.ROLE_CHEF);
 }
 
 @GetMapping("/getSubordinates/{matricule}")
 public ResponseEntity<?> getSubordinates(@PathVariable String matricule) {
     Optional<Personnel> chefOpt = userRepository.findByMatriculeP(matricule);
     if (!chefOpt.isPresent()) {
         return ResponseEntity.notFound().build();
     }

     Personnel chef = chefOpt.get();
     if (chef.getNiveau() == null) {
         return ResponseEntity.badRequest().body("User is not a chef.");
     }

     List<Personnel> subordinates;
     switch (chef.getNiveau()) {
         case 1:
             // All in service
             subordinates = userRepository.findByServIdService(chef.getServ().getIdService());
             break;
         case 2:
             // Direct + indirect
             subordinates = userRepository.findDirectAndIndirectSubordinates(chef.getIdEmploye());
             break;
         case 3:
             // Direct only
             subordinates = userRepository.findByResponsable_IdEmploye(chef.getIdEmploye());
             break;
         default:
             subordinates = Collections.emptyList();
     }

     return ResponseEntity.ok(subordinates);
 }
 
 }

