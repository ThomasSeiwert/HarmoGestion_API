package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.exceptions.ModelException;
import fr.afpa.cda19.harmogestionapi.models.Representation;
import fr.afpa.cda19.harmogestionapi.repositories.RepresentationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service pour les représentations.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@Data
@Service
public class RepresentationService {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Instance de la repository des représentations.
     */
    private RepresentationRepository representationRepository;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur du service des représentations.
     *
     * @param representationRepository instance de la repository
     */
    @Autowired
    public RepresentationService(final RepresentationRepository representationRepository) {

        this.representationRepository = representationRepository;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Service pour chercher toutes les représentations.
     *
     * @return Iterable{Representation} : la liste des représentations
     */
    public Iterable<Representation> getProchainesRepresentations() {

        return representationRepository.findAllInFuture();
    }

    /**
     * Service pour chercher une représentation selon son identifiant.
     *
     * @param id int : identifiant de la représentation cherchée
     *
     * @return Optional{Representation} : la représentation correspondante
     */
    public Optional<Representation> getRepresentation(final int id) {

        return representationRepository.findById(id);
    }

    /**
     * Service pour créer une représentation.
     *
     * @param representation Representation : représentation à créer
     *
     * @return Representation : la représentation créée
     */
    public Representation createRepresentation(final Representation representation) {

        if (representation.getIdRepresentation() != null) {
            throw new ModelException("L'identifiant doit être nul");
        }
        return representationRepository.save(representation);
    }

    /**
     * Service pour modifier une représentation.
     *
     * @param representation Representation : représentation à modifier
     *
     * @return Representation : la représentation modifiée
     */
    public Representation updateRepresentation(final Representation representation) {

        if (representation.getIdRepresentation() == null) {
            throw new ModelException("L'identifiant doit être non nul");
        }
        return representationRepository.save(representation);
    }

    /**
     * Service pour supprimer une représentation selon son identifiant.
     *
     * @param id int : identifiant de la représentation à supprimer
     */
    public void deleteRepresentation(final int id) {

        representationRepository.deleteById(id);
    }
}
