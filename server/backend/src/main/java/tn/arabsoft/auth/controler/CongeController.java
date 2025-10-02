package tn.arabsoft.auth.controler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.arabsoft.auth.entity.ChartConge;
import tn.arabsoft.auth.entity.Conge;
import tn.arabsoft.auth.entity.ERole;
import tn.arabsoft.auth.entity.Personnel;
import tn.arabsoft.auth.entity.Sanction;
import tn.arabsoft.auth.entity.Service;
import tn.arabsoft.auth.entity.TypeConge;
import tn.arabsoft.auth.entity.Notification;
import tn.arabsoft.auth.repository.ChartRepo;
import tn.arabsoft.auth.repository.CongeRepository;
import tn.arabsoft.auth.repository.NotificationRepository;
import tn.arabsoft.auth.repository.SanctionRepository;
import tn.arabsoft.auth.repository.TypeCongeRepository;
import tn.arabsoft.auth.repository.UserRepository;
import tn.arabsoft.auth.ws.NotificationWSController;
import tn.arabsoft.auth.payload.request.NotificationDTO;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/conge")
public class CongeController {

	
	@Autowired
	CongeRepository congeRepository;
	@Autowired
	TypeCongeRepository typeconge;
	@Autowired
	SanctionRepository sanctionRepository;
	@Autowired
	ChartRepo chartRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationWSController notificationWS;
	
	
	@Autowired
	NotificationRepository notificationRepository;
	

	@GetMapping("/get/{mat}")
	public List<Conge> getCongeByMat(@PathVariable("mat") String mat)
	{
		return congeRepository.getCongeByMat(mat);
	}
	
	@GetMapping("/getCng/{mat}")
	public List<Object[]> getCongeByMonth(@PathVariable("mat") String mat)
	{
		return congeRepository.getCongeByM(mat);
	}
	
	@GetMapping("/getNbrCng/{mat}")
	public Long getNbrConge(@PathVariable("mat") String mat)
	{
		return congeRepository.getNbrCng(mat);
	}
	
	@GetMapping("/getNbrCngToday")
	public Long getNbrCngAujourdhui() {
	    return congeRepository.getNbrCngAujourdhui();
	}

@PostMapping("/addSanction")
public Sanction addSanction(@RequestBody Sanction sanc)
{
	sanc.setNomSnaction("Vous avez depassez ton solde conges");
	
	return sanctionRepository.save(sanc);
	
}


@GetMapping("/notifications/{matricule}")
public List<NotificationDTO> getUnreadNotifications(@PathVariable String matricule) {
    return notificationRepository.findByTargetMatriculeAndIsReadFalse(matricule)
        .stream()
        .map(n -> new NotificationDTO(n.getId(), n.getMessage(), n.isRead(), n.getCreatedAt()))
        .collect(Collectors.toList());
}

@CrossOrigin(origins = "*")
@PutMapping("/notifications/read/{id}")
public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
    Optional<Notification> notifOpt = notificationRepository.findById(id);
    if (notifOpt.isPresent()) {
        Notification notif = notifOpt.get();
        notif.setRead(true);
        notificationRepository.save(notif);
        return ResponseEntity.ok().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
@GetMapping("/notifications/all/{matricule}")
public List<NotificationDTO> getAllNotifications(@PathVariable String matricule) {
    return notificationRepository.findByTargetMatricule(matricule)
        .stream()
        .map(n -> new NotificationDTO(n.getId(), n.getMessage(), n.isRead(), n.getCreatedAt()))
        .collect(Collectors.toList());
}



/*
@PostMapping("/add")
public  ResponseEntity<?> createConge(@RequestBody Conge conge) {
    Integer idEmploye = conge.getPersonnel().getIdEmploye();
    Personnel submitter = userRepository.findById(idEmploye)
        .orElseThrow(() -> new RuntimeException("Personnel not found!"));
    
    // Validate submitter role
    boolean isPersonnel = submitter.getRoles().stream()
        .anyMatch(role -> role.getNomRole() == ERole.ROLE_PERSONNEL);
    boolean isChef = submitter.getRoles().stream()
        .anyMatch(role -> role.getNomRole() == ERole.ROLE_CHEF);
    
    if (!isPersonnel && !isChef) {
        throw new RuntimeException("Submitter must be a personnel or chef!");
    }

    // Set basic conge information
    conge.setPersonnel(submitter);
    conge.setMatriculeP(submitter.getMatriculeP());
    conge.setNom(submitter.getNom());
    conge.setPrenom(submitter.getPrenom());
    conge.setStatut("Pending");
    
    // Clear all approval fields
    conge.setRepNiveau3(null);
    conge.setRepNiveau2(null);
    conge.setRepNiveau1(null);
    conge.setRepRh(null);
    conge.setRepChef(null);

    Service submitterService = submitter.getServ();
    if (submitterService == null) {
        throw new RuntimeException("Submitter must belong to a service!");
    }

    // Traverse the approval hierarchy
    Personnel currentResponsable = submitter.getResponsable();
    boolean needsChefApproval = false;

    while(currentResponsable != null) {
        // Validate service consistency
        if(!submitterService.equals(currentResponsable.getServ())) {
            throw new RuntimeException("Responsable " + currentResponsable.getMatriculeP() 
                + " is not in the same service!");
        }

        // Validate niveau hierarchy
        if(isChef && currentResponsable.getNiveau() >= submitter.getNiveau()) {
            throw new RuntimeException("Responsable must have higher niveau than submitter!");
        }

        // Set pending approvals based on niveau
        switch(currentResponsable.getNiveau()) {
            case 3:
                conge.setRepNiveau3("Pending");
                needsChefApproval = true;
                break;
            case 2:
                conge.setRepNiveau2("Pending");
                needsChefApproval = true;
                break;
            case 1:
                conge.setRepNiveau1("Pending");
                needsChefApproval = true;
                break;
            default:
                throw new RuntimeException("Invalid niveau value for responsable!");
        }

        // Move up the hierarchy
        currentResponsable = currentResponsable.getResponsable();
        
        // Stop if reached top niveau (1)
        if(currentResponsable != null && currentResponsable.getNiveau() == 1) {
            break;
        }
    }

    // If no chef approvals needed, go directly to RH
    if(!needsChefApproval) {
        conge.setRepRh("Pending");
    }
    Long sld = this.congeRepository.getMaxSolde(conge.getMatriculeP());
    long elapsedms = conge.getDateFin().getTime() - conge.getDateDebut().getTime();
    long differenceDays = elapsedms / (24 * 60 * 60 * 1000);
    
    long defaultInitialBalance = 21;
    
    if (sld != null) {
        conge.setSoldeCng(sld - differenceDays);
    } else {
    	  conge.setSoldeCng(defaultInitialBalance - differenceDays);
    }
    
    
    
    
    
    
    
    
    

    Conge savedConge = congeRepository.save(conge);
 // Notify RH 
    if ("Pending".equals(savedConge.getRepRh())
    	    && "O".equals(savedConge.getRepNiveau1())
    	    && "O".equals(savedConge.getRepNiveau2())
    	    && "O".equals(savedConge.getRepNiveau3())) {
    	    
    	    List<Personnel> rhList = userRepository.findAll().stream()
    	        .filter(p -> p.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_ADMIN))
    	        .collect(Collectors.toList());

    	    for (Personnel rh : rhList) {
    	        String rhMsg = "Demande de congé à valider par RH de " + submitter.getNom() + " " + submitter.getPrenom();
    	        Notification notif = new Notification(rh.getMatriculeP(), rhMsg);
    	        notificationRepository.save(notif);
    	        notificationWS.sendNotification(notif);
    	    }
    	}
 // Notification chefs
    Personnel current = submitter.getResponsable();
    while (current != null) {
        if (current.getServ().equals(submitterService)) {
            int niveau = current.getNiveau();
            boolean isTarget = false;

            switch (niveau) {
                case 3:
                    isTarget = "Pending".equals(conge.getRepNiveau3());
                    break;
                case 2:
                    isTarget = "O".equals(conge.getRepNiveau3()) && "Pending".equals(conge.getRepNiveau2());
                    break;
                case 1:
                    isTarget = "O".equals(conge.getRepNiveau2()) && "Pending".equals(conge.getRepNiveau1());
                    break;
            }

            if (isTarget) {
                String msg = "Nouvelle demande de congé à traiter de " + submitter.getNom() + " " + submitter.getPrenom();
                Notification notif = new Notification(current.getMatriculeP(), msg);
                notificationRepository.save(notif);
                notificationWS.sendNotification(notif);
                break; // Notifier uniquement le responsable concerné
            }
        }
        current = current.getResponsable();
    }
    
    
    
    
    
    
    
  
    // Check if soldeCng is ≤ 0 and apply sanction
    if (savedConge.getSoldeCng() <= 0) {
        Sanction sanction = new Sanction();
        sanction.setNomSnaction("Vous avez depassez ton solde conges");
        sanction.setDateSanction(new Date());
        sanction.setMatriculeP(savedConge.getMatriculeP());
        sanction.setPersonnel(savedConge.getPersonnel());
        sanctionRepository.save(sanction);
    }
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Leave request submitted for approval. Approval path: " + getApprovalPath(conge));
    response.put("conge", savedConge);
    return ResponseEntity.ok(response);
}

*/



@PostMapping("/add")
public ResponseEntity<?> createConge(@RequestBody Conge conge) {
    Integer idEmploye = conge.getPersonnel().getIdEmploye();
    Personnel submitter = userRepository.findById(idEmploye)
        .orElseThrow(() -> new RuntimeException("Personnel not found!"));

    boolean isPersonnel = submitter.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_PERSONNEL);
    boolean isChef = submitter.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_CHEF);

    if (!isPersonnel && !isChef) {
        throw new RuntimeException("Submitter must be a personnel or chef!");
    }

    conge.setPersonnel(submitter);
    conge.setMatriculeP(submitter.getMatriculeP());
    conge.setNom(submitter.getNom());
    conge.setPrenom(submitter.getPrenom());
    conge.setStatut("Pending");
    conge.setRepNiveau3(null);
    conge.setRepNiveau2(null);
    conge.setRepNiveau1(null);
    conge.setRepRh(null);
    conge.setRepChef(null);

    Service submitterService = submitter.getServ();
    if (submitterService == null) {
        throw new RuntimeException("Submitter must belong to a service!");
    }

    // Appliquer la hiérarchie
    Personnel current = submitter.getResponsable();
    boolean hasNotified = false;

    while (current != null) {
        if (!submitterService.equals(current.getServ())) {
            current = current.getResponsable();
            continue;
        }

        switch (current.getNiveau()) {
            case 3:
                conge.setRepNiveau3("Pending");
                
                break;
            case 2:
                conge.setRepNiveau2("Pending");
                break;
            case 1:
                conge.setRepNiveau1("Pending");
                break;
        }
        if (!hasNotified) {
            notifyChef(current, submitter);
            hasNotified = true;
        }

        current = current.getResponsable();
    }

    if (!hasNotified && conge.getRepRh() == null &&
        conge.getRepNiveau3() == null &&
        conge.getRepNiveau2() == null &&
        conge.getRepNiveau1() == null) {
        conge.setRepRh("Pending");

        // 🔔 Notification RH
        List<Personnel> rhList = userRepository.findAll().stream()
            .filter(p -> p.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_ADMIN))
            .collect(Collectors.toList());

        for (Personnel rh : rhList) {
            String msg = "Demande de congé à valider par RH de " + submitter.getNom() + " " + submitter.getPrenom();
            Notification notif = new Notification(rh.getMatriculeP(), msg);
            notificationRepository.save(notif);
            notificationWS.sendNotification(notif);
        }
    }

    int currentYear = conge.getDateDebut().toLocalDate().getYear();

 // Total des jours déjà pris cette année
 Long joursPris = congeRepository.sumDureeByMatriculeAndYear(conge.getMatriculeP(), currentYear);
 if (joursPris == null) joursPris = 0L;

 // Durée du nouveau congé
 long nouvelleDuree = (conge.getDateFin().getTime() - conge.getDateDebut().getTime()) / (24 * 60 * 60 * 1000);

 // Solde restant
 long soldeRestant = 21 - (joursPris + nouvelleDuree);

 // Set dans l'objet
 conge.setDuree((int) nouvelleDuree);
 conge.setSoldeCng(soldeRestant);

    Conge savedConge = congeRepository.save(conge);

    // ⚠️ Sanction si solde < 0
    if (soldeRestant < 0) {
        Sanction sanction = new Sanction();
        sanction.setNomSnaction("Vous avez depassez ton solde conges");
        sanction.setDateSanction(new Date());
        sanction.setMatriculeP(savedConge.getMatriculeP());
        sanction.setPersonnel(savedConge.getPersonnel());
        sanctionRepository.save(sanction);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Leave request submitted for approval.");
    response.put("conge", savedConge);
    return ResponseEntity.ok(response);
}


private void notifyChef(Personnel chef, Personnel demandeur) {
    String msg = "Nouvelle demande de congé à traiter de " + demandeur.getNom() + " " + demandeur.getPrenom();
    Notification notif = new Notification(chef.getMatriculeP(), msg);
    notificationRepository.save(notif);
    notificationWS.sendNotification(notif);
}





// Helper method to show approval path
private String getApprovalPath(Conge conge) {
    StringBuilder path = new StringBuilder();
    if(conge.getRepNiveau3() != null) path.append("Niveau3→");
    if(conge.getRepNiveau2() != null) path.append("Niveau2→");
    if(conge.getRepNiveau1() != null) path.append("Niveau1→");
    if(conge.getRepRh() != null) path.append("RH");
    return path.toString().replaceAll("→$", "");
}

@PutMapping("/approve/niveau3/{id}")
public ResponseEntity<Conge> approveNiveau3(
    @PathVariable Long id,
    @RequestParam String decision) {
    
    Conge conge = congeRepository.findById(id).orElseThrow();
    Personnel approver = conge.getPersonnel().getResponsable();
    
    // Validate approver is in the same service
    if (!approver.getServ().equals(conge.getPersonnel().getServ())) {
        throw new RuntimeException("Approver is not in the same service!");
    }
    
    conge.setRepNiveau3(decision);
    
    if ("O".equals(decision)) {
        Personnel nextResponsable = approver.getResponsable();
        if (nextResponsable != null && nextResponsable.getNiveau() == 2) {
            // Ensure nextResponsable is in the same service
            if (!nextResponsable.getServ().equals(conge.getPersonnel().getServ())) {
                throw new RuntimeException("Next approver is not in the same service!");
            }
            
            
            String msg = "Demande de congé à valider de " + conge.getNom() + " " + conge.getPrenom();
            Notification notif = new Notification(nextResponsable.getMatriculeP(), msg);
            notificationRepository.save(notif);
            notificationWS.sendNotification(notif);
            // Do not modify repNiveau2; it's already pending
        } else {
            // No niveau 2 in service, escalate to RH
            conge.setRepRh("Pending"); // Changed from null to "Pending"
            List<Personnel> rhList = userRepository.findAll().stream()
                    .filter(p -> p.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_ADMIN))
                    .collect(Collectors.toList());

                for (Personnel rh : rhList) {
                    String msg = "Demande de congé à valider par RH de " + conge.getNom() + " " + conge.getPrenom();
                    Notification notif = new Notification(rh.getMatriculeP(), msg);
                    notificationRepository.save(notif);
                    notificationWS.sendNotification(notif);
                }
        }
    } else {
        conge.setStatut("Rejected");
    }
    
    return ResponseEntity.ok(congeRepository.save(conge));
}










@PutMapping("/approve/niveau2/{id}")
public ResponseEntity<Conge> approveNiveau2(
    @PathVariable Long id,
    @RequestParam String decision
) {
    Conge conge = congeRepository.findById(id).orElseThrow();
    Personnel approver = conge.getPersonnel().getResponsable();

    // Vérifie que le valideur est dans le même service
    if (!approver.getServ().equals(conge.getPersonnel().getServ())) {
        throw new RuntimeException("Approver is not in the same service!");
    }
    
    conge.setRepNiveau2(decision);

    if ("O".equals(decision)) {
        // 🔁 Recherche du responsable de niveau 1 dans la hiérarchie
        Personnel current = conge.getPersonnel().getResponsable();
        while (current != null) {
            if (current.getNiveau() == 1 && current.getServ().equals(conge.getPersonnel().getServ())) {
                // ✅ Notifier le niveau 1
                String msg = "Demande de congé à valider de " + conge.getNom() + " " + conge.getPrenom();
                Notification notif = new Notification(current.getMatriculeP(), msg);
                notificationRepository.save(notif);
                notificationWS.sendNotification(notif);
                break;
            }
            current = current.getResponsable();
        }

        // Si aucun niveau 1 trouvé, on envoie à RH directement
        if (current == null) {
            conge.setRepNiveau1("Pending");
            List<Personnel> rhList = userRepository.findAll().stream()
                    .filter(p -> p.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_ADMIN))
                    .collect(Collectors.toList());

            for (Personnel rh : rhList) {
                String msg = "Demande de congé à valider par RH de " + conge.getNom() + " " + conge.getPrenom();
                Notification notif = new Notification(rh.getMatriculeP(), msg);
                notificationRepository.save(notif);
                notificationWS.sendNotification(notif);
            }
        }

    } else {
        conge.setStatut("Rejected");
    }

    return ResponseEntity.ok(congeRepository.save(conge));
}


@PutMapping("/approve/niveau1/{id}")
public ResponseEntity<Conge> approveNiveau1(
    @PathVariable Long id,
    @RequestParam String decision
) {
    Conge conge = congeRepository.findById(id).orElseThrow();
    conge.setRepNiveau1(decision);

    if ("O".equals(decision)) {
        // Forward to RH
        conge.setRepRh("Pending"); // Changed from null to "Pending"
        // ✅ Notification RH
        List<Personnel> rhList = userRepository.findAll().stream()
            .filter(p -> p.getRoles().stream().anyMatch(r -> r.getNomRole() == ERole.ROLE_ADMIN))
            .collect(Collectors.toList());

        for (Personnel rh : rhList) {
            String msg = "Demande de congé à valider par RH de " + conge.getNom() + " " + conge.getPrenom();
            Notification notif = new Notification(rh.getMatriculeP(), msg);
            notificationRepository.save(notif);
            notificationWS.sendNotification(notif);
        }
    } else {
        conge.setStatut("Rejected");
    }

    return ResponseEntity.ok(congeRepository.save(conge));
}







//Get demands pending at niveau 3
@GetMapping("/getDemandeNiveau3/{serv}")
public List<Conge> getDemandeNiveau3(@PathVariable("serv") Long  serv) {
 return congeRepository.getDemandeByNiveau3(serv);
}

//Get demands pending at niveau 2
@GetMapping("/getDemandeNiveau2/{serv}")
public List<Conge> getDemandeNiveau2(@PathVariable("serv") Long serv) {
 return congeRepository.getDemandeByNiveau2(serv);
}

//Get demands pending at niveau 1
@GetMapping("/getDemandeNiveau1/{serv}")
public List<Conge> getDemandeNiveau1(@PathVariable("serv")Long  serv) {
 return congeRepository.getDemandeByNiveau1(serv);
}








//Get historique for niveau 3 approvals/rejections
@GetMapping("/getrepDemandeNiveau3/{serv}")
public List<Conge> getrepDemandeNiveau3(@PathVariable("serv") String serv) {
 return congeRepository.getDemandeNiveau3(serv);
}

//Get historique for niveau 2 approvals/rejections
@GetMapping("/getrepDemandeNiveau2/{serv}")
public List<Conge> getrepDemandeNiveau2(@PathVariable("serv") String serv) {
 return congeRepository.getDemandeNiveau2(serv);
}

//Get historique for niveau 1 approvals/rejections
@GetMapping("/getrepDemandeNiveau1/{serv}")
public List<Conge> getrepDemandeNiveau1(@PathVariable("serv") String serv) {
 return congeRepository.getDemandeNiveau1(serv);
}























	@GetMapping("/getTypeConge")
	public List<TypeConge> getTypeConge()
	{
		return typeconge.findAll();
	}
	
	@GetMapping("/getMaxSolde/{mat}")
	public Long getMaxSoldeCng(@PathVariable("mat") String mat)
	{
		return congeRepository.getMaxSolde(mat);
	}
	@GetMapping("/getDemandeChef/{serv}")
	public List<Conge> getDemandeChef(@PathVariable("serv") String serv)
	{
		return congeRepository.getDemandeChef(serv);
	}
	@GetMapping("/getDemandeChefNotNull/{serv}")
	public List<Conge> getDemandeChefNotNull(@PathVariable("serv") String serv)
	{
		return congeRepository.getDemandeChefRepNotNull(serv);
	}
	
	@GetMapping("/getDemandeRhNotNull")
	public List<Conge> getDemandeRhNotNull()
	{
		return congeRepository.getDemandeRhRepNotNull();
	}
	
	@GetMapping("/getDemandeRh")
	public List<Conge> getDemandeRh()
	{
		return congeRepository.getDemandeRh();
	}
	@GetMapping("/getRepChef/{mat}/{id}")
	public String getRepChef(@PathVariable("mat") String mat,@PathVariable("id") Long id)
	{
		return congeRepository.getRepChef(mat,id);
	}
	@DeleteMapping(value = "/{id}")
	public boolean deleteHotel(@PathVariable Long id) {
		
		congeRepository.deleteById(id);
		return true;
	}
	 @CrossOrigin
	  @PutMapping("/updateChef")
	  public ResponseEntity<Conge> updateUclt( @RequestBody Conge Ag) {
		  
		     Optional<Conge> AgData = congeRepository.findById(Ag.getIdConge());
		    if (AgData.isPresent()) {
		    	Conge agg = AgData.get();
		   agg.setRepChef(Ag.getRepChef());
		      
		     return new ResponseEntity<>(congeRepository.save(agg), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    } }
	 
	 @CrossOrigin
	 @PutMapping("/updateRh")
	 public ResponseEntity<Conge> updateRh(@RequestBody Conge Ag) {
	     Optional<Conge> AgData = congeRepository.findById(Ag.getIdConge());
	     if (AgData.isPresent()) {
	         Conge agg = AgData.get();

	         // Check if all non-null repNiveau fields are approved ("O")
	         boolean isNiveau3Approved = (agg.getRepNiveau3() == null) || "O".equals(agg.getRepNiveau3());
	         boolean isNiveau2Approved = (agg.getRepNiveau2() == null) || "O".equals(agg.getRepNiveau2());
	         boolean isNiveau1Approved = (agg.getRepNiveau1() == null) || "O".equals(agg.getRepNiveau1());

	         if (isNiveau3Approved && isNiveau2Approved && isNiveau1Approved) {
	             agg.setRepRh(Ag.getRepRh());
	             agg.setStatut("O".equals(Ag.getRepRh()) ? "Approved" : "Rejected");
	          // ✅ Notification si RH approuve
	             if ("O".equals(Ag.getRepRh())) {
	                 String message = "✅ Votre congé du " + agg.getDateDebut() + " au " + agg.getDateFin() + " a été approuvé.";
	                 Notification confirmation = new Notification(agg.getMatriculeP(), message);
	                 notificationRepository.save(confirmation);
	                 notificationWS.sendNotification(confirmation);
	             }
	             return new ResponseEntity<>(congeRepository.save(agg), HttpStatus.OK);
	         } else {
	             throw new RuntimeException("RH cannot approve until all required chefs approve!");
	         }
	     } else {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }

	 
	 
	 @GetMapping("/getSanction/{mat}")
		public List<Sanction> getSanction(@PathVariable String mat)
		{
			return sanctionRepository.getSancByMat(mat);
		}
	 @GetMapping("/getTotalCongeThisYear/{mat}/{year}")
	 public Long getDureeYear(@PathVariable String mat, @PathVariable int year) {
	     return congeRepository.sumDureeByMatriculeAndYear(mat, year);
	 }
}
