package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.dto.MembreDTO;
import fr.afpa.cda19.harmogestionapi.services.MembreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
 * Contrôleur de gestion des requêtes concernant les membres.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 10/04/2026
 */
@RestController
public class MembreController {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Service de liaison avec le repository des membres.
     */
    private final MembreService membreService;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur d'initialisation du contrôleur.
     *
     * @param membreService Sevice de liaison avec le repository des membres.
     */
    @Autowired
    public MembreController(final MembreService membreService) {

        this.membreService = membreService;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Endpoint de récupèration de la liste des membres.
     *
     * @return la liste des membres et statut 200,
     * ou statut 204 si aucun membre trouvé
     */
    @GetMapping("/membres")
    public ResponseEntity<Iterable<Membre>> getMembres() {

        ArrayList<Membre> membres =
                (ArrayList<Membre>) membreService.getMembres();

        if (membres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(membres, HttpStatus.OK);
        }
    }

    /**
     * Endpoint de récupèration d'un membre par son identifiant.
     *
     * @param id Identifiant du membre à récupérer
     *
     * @return Réponse contenant le membre correspondant avec un code 200
     * ou un code 400 si aucun membre ne corrspond à cet identifiant
     */
    @GetMapping("/membre/{id}")
    public ResponseEntity<Membre> getMembre(
            @PathVariable final int id) {

        Optional<Membre> membre = membreService.getMembre(id);

        if (membre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(membre.get(), HttpStatus.OK);
        }
    }

    /**
     * Endpoint de création d'un nouveau membre.
     *
     * @param membre Membre à créer
     * @param result Résultat de la validation du membre
     *
     * @return La response contenant le membre créé avec un code 201,
     * ou un code 400 si le membre est invalide
     */
    @PostMapping("/membre")
    public ResponseEntity<Membre> createMembre(
            @RequestBody
            @Valid final MembreDTO membre, final BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Membre persistantMembre = Membre.clone(membre);
            Membre savedMembre = membreService.createMembre(persistantMembre);
            return new ResponseEntity<>(savedMembre, HttpStatus.CREATED);
        }
    }

    /**
     * Endpoint de mise à jour d'un membre.
     *
     * @param id     Identifiant du membre à modifier
     * @param membre Membre modifié.
     * @param result Résultat de la validation du membre modifié.
     *
     * @return Réponse contenant le membre modifié avec un code 200
     * ou un code 400 si le membre est invalide
     */
    @PutMapping("/membre/{id}")
    public ResponseEntity<Membre> updateMembre(
            @PathVariable final int id,
            @RequestBody
            @Valid final MembreDTO membre, final BindingResult result) {

        Optional<Membre> optionalMembre = membreService.getMembre(id);
        if (optionalMembre.isEmpty()) {
            // le membre doit exister dans la BDD
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (result.hasErrors()) {
            // le membre ne doit pas avoir d'erreurs
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Membre persistantMembre = Membre.clone(membre);
            Membre savedMembre = membreService.updateMembre(persistantMembre);
            return new ResponseEntity<>(savedMembre, HttpStatus.OK);
        }
    }

    /**
     * Endpoint de suppression d'un membre.
     *
     * @param id Identifiant du membre à supprimer
     */
    @DeleteMapping("/membre/{id}")
    public ResponseEntity<Object> deleteMembre(
            @PathVariable final int id) {

        Optional<Membre> optionalMembre = membreService.getMembre(id);
        if (optionalMembre.isEmpty()) {
            // le membre doit exister dans la BDD
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                membreService.deleteMembre(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (DataIntegrityViolationException _) {
                // le membre ne doit pas être utilisé
                return new ResponseEntity<>("Vous ne pouvez pas supprimer"
                        + " ce membre car il est encore utilisé",
                        HttpStatus.FORBIDDEN);
            }
        }
    }
}
