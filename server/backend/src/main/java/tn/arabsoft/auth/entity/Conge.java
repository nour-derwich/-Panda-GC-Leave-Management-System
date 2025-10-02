package tn.arabsoft.auth.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
@Entity
@Table(name="conge")
public class Conge implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long  idConge ; 
	
	@Column(name = "matriculep")
	private String MatriculeP ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp 
    @Column(name = "date_cng")
	private Date dateCng  ;
    
    

	private Date dateDebut  ;
	private  Date dateFin ;
	private int duree;
	private String statut ;
	private Long soldeCng;
	private String repChef ;
	private String repRh ;
	private String repNiveau3;  // Approval from niveau 3 chef (same service)
	private String repNiveau2;  // Approval from niveau 2 chef (same service)
	private String repNiveau1;  // Approval from niveau 1 chef (same service)
private String nom;
private String prenom;
	@ManyToOne
	@JoinColumn(name="idEmploye")
	@JsonProperty(access = Access.WRITE_ONLY)

	private Personnel personnel;
	


	@ManyToOne
	@JoinColumn(name="idType")
	private TypeConge typeConge;



	public Conge() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Conge(String matriculeP, Date dateDebut, Date dateFin, int duree, String statut, Personnel personnel,
			TypeConge typeConge) {
		super();
		MatriculeP = matriculeP;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.duree = duree;
		this.statut = statut;
		this.personnel = personnel;
		this.typeConge = typeConge;
	}

	public String getRepChef() {
		return repChef;
	}



	public Date getDateCng() {
		return dateCng;
	}



	public void setDateCng(Date dateCng) {
		this.dateCng = dateCng;
	}



	public void setRepChef(String repChef) {
		this.repChef = repChef;
	}
	public String getRepRh() {
		return repRh;
	}

	public void setRepRh(String repRh) {
		this.repRh = repRh;
	}



	public Long getSoldeCng() {
		return soldeCng;
	}



	public void setSoldeCng(Long soldeCng) {
		this.soldeCng = soldeCng;
	}



	public Long getIdConge() {
		return idConge;
	}



	public void setIdConge(Long idConge) {
		this.idConge = idConge;
	}



	public String getMatriculeP() {
		return MatriculeP;
	}



	public void setMatriculeP(String matriculeP) {
		MatriculeP = matriculeP;
	}



	public Date getDateDebut() {
		return dateDebut;
	}



	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}



	public Date getDateFin() {
		return dateFin;
	}



	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	public int getDuree() {
		return duree;
	}



	public void setDuree(int duree) {
		this.duree = duree;
	}



	public String getStatut() {
		return statut;
	}



	public void setStatut(String statut) {
		this.statut = statut;
	}



	public Personnel getPersonnel() {
		return personnel;
	}



	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}



	public TypeConge getTypeConge() {
		return typeConge;
	}



	public void setTypeConge(TypeConge typeConge) {
		this.typeConge = typeConge;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getRepNiveau3() { return repNiveau3; }
	public void setRepNiveau3(String repNiveau3) { this.repNiveau3 = repNiveau3; }

	public String getRepNiveau2() { return repNiveau2; }
	public void setRepNiveau2(String repNiveau2) { this.repNiveau2 = repNiveau2; }

	public String getRepNiveau1() { return repNiveau1; }
	public void setRepNiveau1(String repNiveau1) { this.repNiveau1 = repNiveau1; }
	

}