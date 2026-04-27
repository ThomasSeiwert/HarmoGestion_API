package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.dto.CoursDTO;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Controller pour les cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 07/04/2026
 */
@RestController
public class CoursController {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Instance du service pour les cours.
     */
    private final CoursService coursService;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur du controller des cours.
     *
     * @param coursService instance du service des cours
     */
    @Autowired
    public CoursController(final CoursService coursService) {

        this.coursService = coursService;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Pour une requête GET et une URL "/cours", retourne la liste des prochains cours.
     *
     * @return la liste des prochains cours et statut 200,
     * ou statut 204 si aucun cours trouvé
     */
    @GetMapping("/cours")
    public ResponseEntity<Iterable<Cours>> getProchainsCours() {

        ArrayList<Cours> listeCours =
                (ArrayList<Cours>) coursService.getProchainsCours();

        if (listeCours.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(listeCours, HttpStatus.OK);
        }
    }

    /**
     * Pour une requête GET et une URL "/cours/'id',
     * retourne le cours correspondant à l'identifiant demandé.
     *
     * @param id int : identifiant du cours demandé
     *
     * @return le cours correspondant à l'id et statut 200,
     * ou statut 400 si aucun cours ne correspondant à l'identifiant
     */
    @GetMapping("/cours/{id}")
    public ResponseEntity<Cours> getCours(
            @PathVariable final int id) {

        Optional<Cours> cours = coursService.getCours(id);

        if (cours.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(cours.get(), HttpStatus.OK);
        }
    }

    /**
     * Pour une requête POST et une URL "/cours", crée le cours demandé.
     *
     * @param cours  Cours : cours à créer
     * @param result BindingResult : erreurs de validation par rapport au model
     *
     * @return le cours créé et un statut 201, ou 400 pour un cours non valide
     */
    @PostMapping("/cours")
    public ResponseEntity<Cours> createCours(
            @RequestBody
            @Valid final CoursDTO cours, final BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Cours persistantCours = Cours.clone(cours);
            Cours savedCours = coursService.createCours(persistantCours);
            return new ResponseEntity<>(savedCours, HttpStatus.CREATED);
        }
    }

    /**
     * Pour une requête PUT et une URL "/cours/'id'", modifie le cours demandé.
     *
     * @param id     int : identifiant du cours à modifier
     * @param cours  Cours : cours à modifier
     * @param result BindingResult : erreurs de validation par rapport au model
     *
     * @return cours modifié avec un statut 200, ou 400 pour un cours non valide
     */
    @PutMapping("/cours/{id}")
    public ResponseEntity<Cours> updateCours(
            @PathVariable final int id,
            @RequestBody
            @Valid final CoursDTO cours, final BindingResult result) {

        Optional<Cours> optionalCours = coursService.getCours(id);
        if (optionalCours.isEmpty() || result.hasErrors()) {
            // le cours doit exister dans la BDD et ne doit pas avoir d'erreurs
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Cours persistantCours = Cours.clone(cours);
            Cours savedCours = coursService.updateCours(persistantCours);
            return new ResponseEntity<>(savedCours, HttpStatus.OK);
        }
    }

    /**
     * Pour une requête DELETE et une URL "/cours/'id'",
     * supprime le cours demandé.
     *
     * @param id int : identifiant du cours à supprimer
     *
     * @return statut 200, ou 400 si aucun cours ne corrspond à l'identifiant
     */
    @DeleteMapping("/cours/{id}")
    public ResponseEntity<Cours> deleteCours(
            @PathVariable final int id) {

        Optional<Cours> optionalCours = coursService.getCours(id);
        if (optionalCours.isEmpty()) {
            // le cours doit exister dans la BDD
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            coursService.deleteCours(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
