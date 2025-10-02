package tn.arabsoft.auth.repository;

import java.util.List;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.arabsoft.auth.entity.ChartConge;
import tn.arabsoft.auth.entity.Conge;
import tn.arabsoft.auth.entity.Service;

public interface CongeRepository extends JpaRepository<Conge, Long> {

	@Query(value="select * from conge where matriculep=:mat",nativeQuery=true)
	public List<Conge> getCongeByMat(@Param("mat") String mat);
	
	
	@Query(value = "SELECT COUNT(*) AS total, DATE_FORMAT(date_cng, '%m/%Y') AS mois " +
	        "FROM conge " +
	        "WHERE matriculep = :mat " +
	        "AND rep_niveau1 = 'O' " +
	        "GROUP BY DATE_FORMAT(date_cng, '%m/%Y')", nativeQuery = true)
	List<Object[]> getCongeByM(@Param("mat") String mat);

	
	
	
	@Query(value="SELECT solde_cng FROM `conge` WHERE matriculep=:mat ORDER BY date_Cng DESC LIMIT 1\r\n",nativeQuery=true)
	public Long getMaxSolde(@Param("mat") String mat);
	
	@Query(value="SELECT rep_chef FROM `conge` WHERE matriculep=:mat and id_conge=:id\r\n",nativeQuery=true)
	public String getRepChef(@Param("mat") String mat,@Param("id")Long id);
	@Query(value="SELECT c.*,(p.nom)name,(p.prenom)lname FROM `conge` c ,personnel p WHERE c.matriculeP=p.matriculeP and "
			+ "c.matriculeP in (select matriculeP from personnel where serv=:serv)and c.rep_chef IS NULL\r\n",nativeQuery=true)
	public List<Conge> getDemandeChef(@Param("serv") String serv);
	
	@Query(value="SELECT c.*,p.nom,p.prenom FROM `conge` c ,personnel p WHERE c.matriculeP=p.matriculeP and c.matriculeP in (select matriculeP from personnel where serv=:serv) and c.rep_chef in ('O','N')",nativeQuery=true)
	public List<Conge> getDemandeChefRepNotNull(@Param("serv") String serv);
	
	
	@Query(value = "SELECT c.*, p.nom, p.prenom " +
		       "FROM conge c, personnel p " +
		       "WHERE c.matriculeP = p.matriculeP " +
		       "AND c.rep_niveau3 in ('O','N')  " + //  niveau 3 
		       "AND p.serv = :serv", 
		       nativeQuery = true)
		List<Conge> getDemandeNiveau3(@Param("serv") String serv);
	
	
	
	@Query(value = "SELECT c.*, p.nom, p.prenom " +
		       "FROM conge c, personnel p " +
		       "WHERE c.matriculeP = p.matriculeP " +
		       "AND c.rep_niveau2 in ('O','N')  " + // Only niveau 2 r
		       "AND p.serv = :serv", 
		       nativeQuery = true)
		List<Conge> getDemandeNiveau2(@Param("serv") String serv);
	
	
	
	
	@Query(value = "SELECT c.*, p.nom, p.prenom " +
		       "FROM conge c, personnel p " +
		       "WHERE c.matriculeP = p.matriculeP " +
		       "AND c.rep_niveau1 in ('O','N')  " + // Only niveau 1 rep
		       "AND p.serv = :serv", 
		       nativeQuery = true)
		List<Conge> getDemandeNiveau1(@Param("serv") String serv);
	
	
	@Query(value="SELECT c.*,p.nom,p.prenom FROM `conge` c ,personnel p WHERE c.matriculeP=p.matriculeP "
			+ " and c.rep_rh in('O','N')",nativeQuery=true)
	public List<Conge> getDemandeRhRepNotNull();
	
	@Query(value = "SELECT c.*, p.nom, p.prenom " +
            "FROM conge c, personnel p " +
            "WHERE c.matriculeP = p.matriculeP " +
            "AND c.rep_rh = 'Pending'", 
     nativeQuery = true)
public List<Conge> getDemandeRh();
	
	@Query(value="select COUNT(idConge) nom ,DATE_FORMAT(date_cng,'%m/%Y') prenom "
			+ "from conge where matriculep=:mat GROUP by DATE_FORMAT(date_cng,'%m-%Y')\r\n",nativeQuery=true)
	public List<Conge> getCngByMonth(@Param("mat") String mat);
	
	
	@Query(value = "SELECT COUNT(*) FROM conge " +
	        "WHERE matriculeP = :mat " +
	        "AND rep_niveau1 = 'O' " +
	        "AND DATE(date_cng) = CURDATE()", nativeQuery = true)
	Long getNbrCng(@Param("mat") String mat);
	
	
	
	
	@Query(value = "SELECT COUNT(*) FROM conge " +
	        "WHERE rep_niveau1 = 'O' " +
	        "AND DATE(date_cng) = CURDATE()", nativeQuery = true)
	Long getNbrCngAujourdhui();
	
	@Query(value = "SELECT SUM(duree) FROM conge " +
	        "WHERE matriculep = :mat AND YEAR(date_debut) = :year AND rep_niveau1 = 'O'", nativeQuery = true)
	Long sumDureeByMatriculeAndYear(@Param("mat") String mat, @Param("year") int year);
	
	
	
	

//@Query("select NEW com.mypackage.CustomerAmountResult(
   // o.customer.surname, sum(o.amount)) 
//from Order as o
//group by o.customer.surname") 
//	SELECT c.* FROM `conge` c ,personnel p WHERE c.matriculeP=p.matriculeP and c.matriculeP in (select matriculeP from personnel where serv=1)
//SELECT c.* FROM conge c,service s,personnel p WHERE s.id_service=p.serv and c.matriculeP=p.matriculeP and p.serv=:serv
	// For niveau 3 chefs
	@Query("SELECT c FROM Conge c WHERE " +
	       "c.personnel.responsable.matriculeP = :mat AND " +
	       "c.repNiveau3 IS NULL AND " +
	       "c.personnel.serv = (SELECT p.serv FROM Personnel p WHERE p.matriculeP = :mat)")
	List<Conge> findPendingForNiveau3(@Param("mat") String matricule);

	// For niveau 2 chefs
	@Query("SELECT c FROM Conge c WHERE " +
	       "c.personnel.responsable.matriculeP = :mat AND " +
	       "c.repNiveau2 IS NULL AND " +
	       "c.personnel.serv = (SELECT p.serv FROM Personnel p WHERE p.matriculeP = :mat)")
	List<Conge> findPendingForNiveau2(@Param("mat") String matricule);

	// For niveau 1 chefs
	@Query("SELECT c FROM Conge c WHERE " +
	       "c.personnel.responsable.matriculeP = :mat AND " +
	       "c.repNiveau1 IS NULL AND " +
	       "c.personnel.serv = (SELECT p.serv FROM Personnel p WHERE p.matriculeP = :mat)")
	List<Conge> findPendingForNiveau1(@Param("mat") String matricule);
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT c FROM Conge c WHERE c.repNiveau1 = 'Pending' AND c.statut != 'Rejected' AND c.personnel.serv.idService = :serv")
	List<Conge> getDemandeByNiveau1(Long  serv);
	
	@Query("SELECT c FROM Conge c WHERE c.repNiveau2 = 'Pending' AND c.statut != 'Rejected' AND c.personnel.serv.idService = :serv")
	List<Conge> getDemandeByNiveau2(Long  serv);
	
	@Query("SELECT c FROM Conge c WHERE c.repNiveau3 = 'Pending' AND c.statut != 'Rejected' AND c.personnel.serv.idService = :serv")
	List<Conge> getDemandeByNiveau3(Long  serv);
	
}
