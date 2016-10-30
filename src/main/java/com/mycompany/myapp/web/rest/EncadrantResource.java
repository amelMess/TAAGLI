package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Encadrant;

import com.mycompany.myapp.repository.EncadrantRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Encadrant.
 */
@RestController
@RequestMapping("/api")
public class EncadrantResource {

    private final Logger log = LoggerFactory.getLogger(EncadrantResource.class);
        
    @Inject
    private EncadrantRepository encadrantRepository;

    /**
     * POST  /encadrants : Create a new encadrant.
     *
     * @param encadrant the encadrant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new encadrant, or with status 400 (Bad Request) if the encadrant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/encadrants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Encadrant> createEncadrant(@RequestBody Encadrant encadrant) throws URISyntaxException {
        log.debug("REST request to save Encadrant : {}", encadrant);
        if (encadrant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("encadrant", "idexists", "A new encadrant cannot already have an ID")).body(null);
        }
        Encadrant result = encadrantRepository.save(encadrant);
        return ResponseEntity.created(new URI("/api/encadrants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("encadrant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /encadrants : Updates an existing encadrant.
     *
     * @param encadrant the encadrant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated encadrant,
     * or with status 400 (Bad Request) if the encadrant is not valid,
     * or with status 500 (Internal Server Error) if the encadrant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/encadrants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Encadrant> updateEncadrant(@RequestBody Encadrant encadrant) throws URISyntaxException {
        log.debug("REST request to update Encadrant : {}", encadrant);
        if (encadrant.getId() == null) {
            return createEncadrant(encadrant);
        }
        Encadrant result = encadrantRepository.save(encadrant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("encadrant", encadrant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /encadrants : get all the encadrants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of encadrants in body
     */
    @RequestMapping(value = "/encadrants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Encadrant> getAllEncadrants() {
        log.debug("REST request to get all Encadrants");
        List<Encadrant> encadrants = encadrantRepository.findAll();
        return encadrants;
    }

    /**
     * GET  /encadrants/:id : get the "id" encadrant.
     *
     * @param id the id of the encadrant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the encadrant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/encadrants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Encadrant> getEncadrant(@PathVariable Long id) {
        log.debug("REST request to get Encadrant : {}", id);
        Encadrant encadrant = encadrantRepository.findOne(id);
        return Optional.ofNullable(encadrant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /encadrants/:id : delete the "id" encadrant.
     *
     * @param id the id of the encadrant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/encadrants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEncadrant(@PathVariable Long id) {
        log.debug("REST request to delete Encadrant : {}", id);
        encadrantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("encadrant", id.toString())).build();
    }

}
