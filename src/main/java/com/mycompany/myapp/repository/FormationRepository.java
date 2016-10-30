package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Formation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Formation entity.
 */
@SuppressWarnings("unused")
public interface FormationRepository extends JpaRepository<Formation,Long> {

    @Query("select distinct formation from Formation formation left join fetch formation.etudiants")
    List<Formation> findAllWithEagerRelationships();

    @Query("select formation from Formation formation left join fetch formation.etudiants where formation.id =:id")
    Formation findOneWithEagerRelationships(@Param("id") Long id);

}
