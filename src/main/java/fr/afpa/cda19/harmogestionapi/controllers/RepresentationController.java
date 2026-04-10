package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Representation;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.services.RepresentationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller pour les représentations.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@RestController
public class RepresentationController {

    /**
     * Instance du service pour les représentations.
     */
    private final RepresentationService representationService;

    /**
     * Constructeur du controller des représentations.
     *
     * @param representationService instance du service des représentations
     */
    @Autowired
    public RepresentationController(final RepresentationService representationService) {
        this.representationService = representationService;
    }

    /**
     * Pour une requête GET et une URL "/representations",
     * retourne la liste des représentations.
     *
     * @return Iterable{Representation} : liste des représentations
     */
    @GetMapping("/representations")
    public Iterable<Representation> getAllRepresentations() {
        return representationService.getAllRepresentations();
    }

    /**
     * Pour une requête GET et une URL "/representations/{id}",
     * retourne la représentation correspondant à l'identifiant demandé.
     *
     * @param id int : identifiant de la représentation demandée.
     * @return Representation : la représentation correspondante (null si inexistante)
     */
    @GetMapping("/representations/{id}")
    public Representation getRepresentation(
            @PathVariable final int id) {

        Optional<Representation> representation = representationService.getRepresentation(id);
        return representation.orElse(null);
    }

    /**
     * Pour une requête POST et une URL "/representations",
     * crée la représentation demandée.
     *
     * @param representation Representation : représentation à créer
     * @param result BindingResult : erreurs de validation
     * @return Réponse avec la représentation créée (201),
     * ou un statut 400 ou 500 pour une erreur.
     */
    @PostMapping("/representations")
    public ResponseEntity<Object> createRepresentation(
            @RequestBody @Valid final Representation representation,
            final BindingResult result) {

        if (representation.getIdRepresentation() != null || result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Vérification des participants
        for (Membre participant : representation.getParticipants()) {
            if (participant.getIdMembre() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        try {
            Representation saved = representationService.saveRepresentation(representation);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException _) {
            return new ResponseEntity<>("Un participant n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (Exception _) {
            return new ResponseEntity<>("Erreur inconnue.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Pour une requête PUT et une URL "/representations/{id}",
     * modifie la représentation demandée.
     *
     * @param id int : identifiant de la représentation à modifier
     * @param representation Representation : représentation modifiée
     * @param result BindingResult : erreurs de validation
     * @return Réponse avec la représentation modifiée (200),
     * ou un statut 400 ou 500 pour une erreur.
     */
    @PutMapping("/representations/{id}")
    public ResponseEntity<Object> updateRepresentation(
            @PathVariable final int id,
            @Valid @RequestBody final Representation representation,
            final BindingResult result) {

        Optional<Representation> optional = representationService.getRepresentation(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("La ressource n'est pas disponible.", HttpStatus.NOT_FOUND);
        }

        if (representation.getIdRepresentation() == null || result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Membre participant : representation.getParticipants()) {
            if (participant.getIdMembre() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        try {
            Representation saved = representationService.saveRepresentation(representation);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (ObjectRetrievalFailureException _) {
            return new ResponseEntity<>("Un participant n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (Exception _) {
            return new ResponseEntity<>("Erreur inconnue.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Pour une requête DELETE et une URL "/representations/{id}",
     * supprime la représentation demandée.
     *
     * @param id int : identifiant de la représentation à supprimer.
     */
    @DeleteMapping("/representations/{id}")
    public void deleteRepresentation(@PathVariable final int id) {
        representationService.deleteRepresentation(id);
    }
}
