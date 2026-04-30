package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.dto.RepresentationDTO;
import fr.afpa.cda19.harmogestionapi.models.Representation;
import fr.afpa.cda19.harmogestionapi.services.RepresentationService;
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
 * Controller pour les représentations.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@RestController
public class RepresentationController {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Instance du service pour les représentations.
     */
    private final RepresentationService representationService;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur du controller des représentations.
     *
     * @param representationService instance du service des représentations
     */
    @Autowired
    public RepresentationController(final RepresentationService representationService) {

        this.representationService = representationService;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Pour une requête GET et une URL "/representations",
     * retourne la liste des représentations.
     *
     * @return la liste des prochaines représentations et statut 200,
     * ou statut 404 si aucune représentation trouvée
     */
    @GetMapping("/representations")
    public ResponseEntity<Object> getProchainesRepresentations() {

        ArrayList<Representation> representations =
                (ArrayList<Representation>) representationService.getProchainesRepresentations();

        if (representations.isEmpty()) {
            return new ResponseEntity<>("Aucune représentation prévue pour le moment",
                    HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(representations, HttpStatus.OK);
        }
    }

    /**
     * Pour une requête GET et une URL "/representation/'id'",
     * retourne la représentation correspondant à l'identifiant demandé.
     *
     * @param id int : identifiant de la représentation demandée.
     *
     * @return la représentation correspondant à l'id et statut 200,
     * ou statut 400 si aucune représentation ne correspondant à l'identifiant
     */
    @GetMapping("/representation/{id}")
    public ResponseEntity<Representation> getRepresentation(
            @PathVariable final int id) {

        Optional<Representation> representation =
                representationService.getRepresentation(id);

        if (representation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(representation.get(), HttpStatus.OK);
        }
    }

    /**
     * Pour une requête POST et une URL "/representation",
     * crée la représentation demandée.
     *
     * @param representation Representation : représentation à créer
     * @param result         BindingResult : erreurs de validation
     *
     * @return la représentation créée et un statut 201,
     * ou 400 pour une représentation non valide
     */
    @PostMapping("/representation")
    public ResponseEntity<Representation> createRepresentation(
            @RequestBody
            @Valid final RepresentationDTO representation, final BindingResult result) {

        if (result.hasErrors() || representation.getIdRepresentation() != null) {
            // la représentation ne doit pas avoir d'erreurs, et avoir un id nul
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Representation persistantRepresentation = Representation.clone(representation);
            Representation savedRepresentation =
                    representationService.saveRepresentation(persistantRepresentation);
            return new ResponseEntity<>(savedRepresentation, HttpStatus.CREATED);
        }
    }

    /**
     * Pour une requête PUT et une URL "/representation/'id'",
     * modifie la représentation demandée.
     *
     * @param id             int : identifiant de la représentation à modifier
     * @param representation Representation : représentation modifiée
     * @param result         BindingResult : erreurs de validation
     *
     * @return représentation modifiée avec un statut 200,
     * ou 400 pour une représentation non valide
     */
    @PutMapping("/representation/{id}")
    public ResponseEntity<Object> updateRepresentation(
            @PathVariable final int id,
            @RequestBody
            @Valid final RepresentationDTO representation, final BindingResult result) {

        Optional<Representation> optionalRepresentation =
                representationService.getRepresentation(id);

        if (optionalRepresentation.isEmpty() || result.hasErrors() ||
                representation.getIdRepresentation() == null || id != representation.getIdRepresentation()) {
            // la représentation doit exister dans la BDD, ne doit pas avoir d'erreurs,
            // doit avoir un id non nul, et avoir le même id que dans l'uri
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Representation persistantRepresentation = Representation.clone(representation);
            Representation savedRepresentation =
                    representationService.saveRepresentation(persistantRepresentation);
            return new ResponseEntity<>(savedRepresentation, HttpStatus.CREATED);
        }
    }

    /**
     * Pour une requête DELETE et une URL "/representation/'id'",
     * supprime la représentation demandée.
     *
     * @param id int : identifiant de la représentation à supprimer.
     */
    @DeleteMapping("/representation/{id}")
    public ResponseEntity<Representation> deleteRepresentation(
            @PathVariable final int id) {

        Optional<Representation> optionalRepresentation =
                representationService.getRepresentation(id);

        if (optionalRepresentation.isEmpty()) {
            // la représentation doit exister dans la BDD
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            representationService.deleteRepresentation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
