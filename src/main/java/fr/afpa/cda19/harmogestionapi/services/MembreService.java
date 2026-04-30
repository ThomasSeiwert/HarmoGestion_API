package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.repositories.MembreRepository;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service de liaison entre les contrôleurs et le repository.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 09/04/2026
 */
@Service
public class MembreService {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Service de liaison avec le repository des membres.
     */
    private final MembreRepository membreRepository;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    /**
     * Constructeur.
     *
     * @param membreRepository Repository des membres
     */
    @Autowired
    public MembreService(MembreRepository membreRepository) {

        this.membreRepository = membreRepository;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Récupèration de la liste des membres.
     *
     * @return Liste des membres
     */
    public Iterable<Membre> getMembres() {

        return membreRepository.findAll();
    }

    /**
     * Récupèration d'un membre par son identifiant.
     *
     * @param id l'identifiant du membre recherché
     *
     * @return éventuel membre correspondant à l'identifiant
     */
    public Optional<Membre> getMembre(final int id) {

        return membreRepository.findById(id);
    }

    /**
     * Enregistrement/modification d'un membre.
     *
     * @param membre Membre à créer/modifier
     *
     * @return Membre après création/modification
     */
    public Membre saveMembre(final Membre membre) {

        return membreRepository.save(membre);
    }

    /**
     * Suppression d'un membre par son identifiant.
     *
     * @param id Identifiant du membre à supprimer
     */
    public void deleteMembre(final int id) {

        membreRepository.deleteById(id);
    }
}
