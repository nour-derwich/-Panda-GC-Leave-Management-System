package tn.arabsoft.auth.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.arabsoft.auth.entity.ERole;
import tn.arabsoft.auth.entity.Personnel;
import tn.arabsoft.auth.entity.Service;
import tn.arabsoft.auth.entity.TypeConge;
import tn.arabsoft.auth.repository.ServiceRepo;
import tn.arabsoft.auth.repository.TypeCongeRepository;
import tn.arabsoft.auth.repository.UserRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    ServiceRepo serviceRepository;
    @Autowired 
    TypeCongeRepository typeCongeRepository;
    @Autowired
    UserRepository userRepository; // Added to access Personnel data

    @PostMapping("/addService")
    public ResponseEntity<?> addService(@RequestBody Service serv) {
        String matriculeChef = serv.getMatriculeChef();
        // Validate matriculeChef
        if (matriculeChef == null || matriculeChef.isEmpty()) {
            return ResponseEntity.badRequest().body("Matricule Chef is required.");
        }
        Optional<Personnel> chefOpt = userRepository.findByMatriculePWithRoles(matriculeChef);
        if (!chefOpt.isPresent()) {
            return ResponseEntity.badRequest().body("No personnel found with matricule: " + matriculeChef);
        }
        Personnel chef = chefOpt.get();
        boolean isChef = chef.getRoles().stream()
            .anyMatch(role -> role.getNomRole() == ERole.ROLE_CHEF);
        if (!isChef) {
            return ResponseEntity.badRequest().body("Personnel " + matriculeChef + " is not a CHEF.");
        }
        // Validation passed, save the service
        Service savedService = serviceRepository.save(serv);
        return ResponseEntity.ok(savedService);
    }

    @PutMapping("/updateService")
    public ResponseEntity<?> updateService(@RequestBody Service serv) {
        Optional<Service> existingServiceOpt = serviceRepository.findById(serv.getIdService());
        
        if (!existingServiceOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Service not found.");
        }

        Service existingService = existingServiceOpt.get();

        // Get the previous chef if exists and reset their service
        if (existingService.getMatriculeChef() != null) {
            Optional<Personnel> oldChefOpt = userRepository.findByMatriculeP(existingService.getMatriculeChef());
            if (oldChefOpt.isPresent()) {
                Personnel oldChef = oldChefOpt.get();
                oldChef.setServ(null); // Remove the service association
                userRepository.save(oldChef);
            }
        }

        // Validate new matriculeChef
        String matriculeChef = serv.getMatriculeChef();
        if (matriculeChef == null || matriculeChef.isEmpty()) {
            return ResponseEntity.badRequest().body("Matricule Chef is required.");
        }

        Optional<Personnel> newChefOpt = userRepository.findByMatriculeP(matriculeChef);
        if (!newChefOpt.isPresent()) {
            return ResponseEntity.badRequest().body("No personnel found with matricule: " + matriculeChef);
        }

        Personnel newChef = newChefOpt.get();
        boolean isChef = newChef.getRoles().stream()
            .anyMatch(role -> role.getNomRole() == ERole.ROLE_CHEF);
        if (!isChef) {
            return ResponseEntity.badRequest().body("Personnel " + matriculeChef + " is not a CHEF.");
        }

        // Update the service
        existingService.setMatriculeChef(matriculeChef);
        existingService.setCod_serv(serv.getCod_serv());
        existingService.setLib_serv(serv.getLib_serv());
        Service updatedService = serviceRepository.save(existingService);

        // Assign the new chef to this service
        newChef.setServ(updatedService);
        userRepository.save(newChef);

        return ResponseEntity.ok(updatedService);
    }


    // Existing methods below remain unchanged
    @PostMapping("/addTypeConge")
    public TypeConge addTypeConge(@RequestBody TypeConge type) {        
        return typeCongeRepository.save(type);    
    }

    @GetMapping("/getTypeConge")
    public List<TypeConge> getTypeConge() {
        return typeCongeRepository.findAll();
    }

    @GetMapping("/getService")
    public List<Service> getService() {
        return serviceRepository.findAll();
    }

    @DeleteMapping(value = "/service/{id}")
    public boolean deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
        return true;
    }

    @DeleteMapping(value = "/type/{id}")
    public boolean deleteType(@PathVariable Long id) {
        typeCongeRepository.deleteById(id);
        return true;
    }
}