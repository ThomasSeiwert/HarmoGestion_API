package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.dto.InstrumentDTO;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
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
 * Contrôleur de gestion des requêtes concernant les instruments.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 08/04/2026
 */
@RestController
public class InstrumentController {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Service de liaison avec le repository des instruments.
     */
    private final InstrumentService instrumentService;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur d'initialisation du contrôleur.
     *
     * @param instrumentService service de liaison avec le repository des instruments
     */
    @Autowired
    public InstrumentController(final InstrumentService instrumentService) {

        this.instrumentService = instrumentService;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Endpoint de récupèration de la liste des instruments.
     *
     * @return la liste des instruments et statut 200,
     * ou statut 204 si aucun instrument trouvé
     */
    @GetMapping("/instruments")
    public ResponseEntity<Iterable<Instrument>> getInstruments() {

        ArrayList<Instrument> instruments =
                (ArrayList<Instrument>) instrumentService.getInstruments();

        if (instruments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(instruments, HttpStatus.OK);
        }
    }

    /**
     * Endpoint de récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à récupérer
     *
     * @return la réponse contenant l'instrument correspondant avec un code 200,
     * ou un code 400 si aucun instrument trouvé
     */
    @GetMapping("/instrument/{id}")
    public ResponseEntity<Instrument> getInstrument(
            @PathVariable final int id) {

        Optional<Instrument> instrument = instrumentService.getInstrument(id);

        if (instrument.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(instrument.get(), HttpStatus.OK);
        }
    }

    /**
     * Endpoint de création d'un nouvel instrument.
     *
     * @param instrument l'instrument à créer
     * @param result     le résultat de la validation de l'instrument
     *
     * @return la response contenant l'instrument créé avec un code 201,
     * ou un code 403 si le libellé de l'instrument existe déjà,
     * ou un code 400 pour un instrument non valide
     */
    @PostMapping("/instrument")
    public ResponseEntity<Object> createInstrument(
            @RequestBody
            @Valid final InstrumentDTO instrument, final BindingResult result) {

        if (result.hasErrors()) {
            // l'instrument ne doit pas avoir d'erreurs
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Instrument persistantInstrument = Instrument.clone(instrument);
            try {
                Instrument savedInstrument =
                        instrumentService.createInstrument(persistantInstrument);
                return new ResponseEntity<>(savedInstrument, HttpStatus.CREATED);
            }
            catch (DataIntegrityViolationException _) {
                // le libellé doit être unique
                return new ResponseEntity<>("Ce libellé existe déjà",
                        HttpStatus.FORBIDDEN);
            }
        }
    }

    /**
     * Endpoint de mise à jour d'un instrument.
     *
     * @param id         l'identifiant de l'instrument à modifier
     * @param instrument L'instrument à modifier
     * @param result     Le résultat de la validation de l'instrument à modifier
     *
     * @return la réponse contenant l'instrument modifié avec un code 200,
     * ou un code 403 si le libellé de l'instrument existe déjà,
     * ou un code 400 pour un instrument non valide
     */
    @PutMapping("/instrument/{id}")
    public ResponseEntity<Object> updateInstrument(
            @PathVariable final int id,
            @RequestBody
            @Valid final InstrumentDTO instrument, final BindingResult result) {

        Optional<Instrument> optionalInstrument = instrumentService.getInstrument(id);
        if (optionalInstrument.isEmpty() || result.hasErrors()) {
            // l'instrument doit exister dans la BDD et ne pas avoir d'erreurs
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            Instrument persistantInstrument = Instrument.clone(instrument);
            try {
                Instrument savedInstrument =
                        instrumentService.updateInstrument(persistantInstrument);
                return new ResponseEntity<>(savedInstrument, HttpStatus.OK);
            }
            catch (DataIntegrityViolationException _) {
                // le libellé doit être unique
                return new ResponseEntity<>("Ce libellé existe déjà",
                        HttpStatus.FORBIDDEN);
            }
        }
    }

    /**
     * Endpoint de suppression d'un instrument.
     *
     * @param id l'identifiant de l'instrument à supprimer
     *
     * @return statut 200,
     * ou 403 si l'instrument est encore utilisé,
     * ou 400 si aucun cours ne corrspond à l'identifiant
     */
    @DeleteMapping("/instrument/{id}")
    public ResponseEntity<Object> deleteInstrument(
            @PathVariable final int id) {

        Optional<Instrument> optionalInstrument = instrumentService.getInstrument(id);
        if (optionalInstrument.isEmpty()) {
            // l'instrument doit exister dans la BDD
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                instrumentService.deleteInstrument(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch (DataIntegrityViolationException _) {
                // l'instrument ne doit pas être utilisé
                return new ResponseEntity<>("Vous ne pouvez pas supprimer"
                        + " cet instrument car il est encore utilisé",
                        HttpStatus.FORBIDDEN);
            }
        }
    }
}
