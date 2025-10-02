package tn.arabsoft.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.arabsoft.auth.entity.ERole;
import tn.arabsoft.auth.entity.Personnel;
import tn.arabsoft.auth.entity.Service;

public interface UserRepository extends JpaRepository<Personnel, Integer>{
	 Optional<Personnel> findByNom(String username);
	  Optional<Personnel> findByMatriculeP(String matricule);
	  Boolean existsByMatriculeP(String mat);
	  @Query(value="SELECT * FROM personnel where id_employe=:id\r\n",nativeQuery=true)
	  public Personnel getpers(@Param("id")Integer id);  
	  
//SELECT matriculep FROM personnel ORDER BY id_employe DESC LIMIT 1
@Query(value="SELECT matriculep FROM personnel ORDER BY id_employe DESC LIMIT 1\r\n",nativeQuery=true)
public String getMat();


// Add this new method
@Query("SELECT p FROM Personnel p LEFT JOIN FETCH p.roles WHERE p.matriculeP = :matricule")
Optional<Personnel> findByMatriculePWithRoles(@Param("matricule") String matricule);

List<Personnel> findByServ_IdServiceAndRoles_NomRole(Long serviceId, ERole nomRole);
List<Personnel> findByServIdService(Long serviceId);
List<Personnel> findByResponsable_IdEmploye(Integer responsableId);
// For Niveau 2: Direct + indirect subordinates (niveau 3's subordinates)
@Query("SELECT p FROM Personnel p WHERE p.responsable.idEmploye = :chefId OR p.responsable.responsable.idEmploye = :chefId")
List<Personnel> findDirectAndIndirectSubordinates(@Param("chefId") Integer chefId);


Optional<Personnel> findFirstByServIdServiceAndRolesNomRoleAndNiveau(
        Long servIdService, 
        ERole nomRole, 
        Integer niveau
    );
	

@Query("SELECT p.niveau FROM Personnel p WHERE p.idEmploye = (SELECT p.responsable.idEmploye FROM Personnel p WHERE p.idEmploye = :idEmploye)")
Optional<Integer> findNiveauOfResponsable(@Param("idEmploye") Integer idEmploye);


Boolean existsByEmail(String email);
}
