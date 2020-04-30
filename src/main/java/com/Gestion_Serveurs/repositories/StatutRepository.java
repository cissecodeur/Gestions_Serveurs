package com.Gestion_Serveurs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Gestion_Serveurs.entities.Statut;

/**
 * Repository : Statut.
 */
@Repository
public interface StatutRepository extends JpaRepository<Statut, Integer> {
	
	@Query("select e from Statut e where e.id = :id ")
	Optional<Statut> findById(@Param("id")Integer id);


	@Query("select e from Statut e where e.libelle = :libelle")
	Statut findByLibelle(@Param("libelle")String libelle);
	

}
