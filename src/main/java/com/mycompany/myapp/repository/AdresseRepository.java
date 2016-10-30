package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Adresse;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Adresse entity.
 */
@SuppressWarnings("unused")
public interface AdresseRepository extends JpaRepository<Adresse,Long> {

}
