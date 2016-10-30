package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Encadrant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Encadrant entity.
 */
@SuppressWarnings("unused")
public interface EncadrantRepository extends JpaRepository<Encadrant,Long> {

}
