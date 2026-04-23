package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.exceptions.ModelException;
import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.repositories.CoursRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service pour les cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 07/04/2026
 */
@Data
@Service
public class CoursService {

    /**
     * Instance de la repository des cours.
     */
    private CoursRepository coursRepository;


    /**
     * Constructeur du service des cours.
     *
     * @param coursRepository instance de la repository des cours
     */
    @Autowired
    public CoursService(final CoursRepository coursRepository) {

        this.coursRepository = coursRepository;
    }


    /**
     * Service pour chercher tous les prochains cours.
     *
     * @return Iterable{Cours} : la liste des prochains cours
     */
    public Iterable<Cours> getProchainsCours() {

        return coursRepository.findAllInFuture();
    }

    /**
     * Service pour chercher un cours selon son identifiant.
     *
     * @param id int : identifiant du cours cherché
     *
     * @return Optional{Cours} : le cours correspondant à l'id (peut être null)
     */
    public Optional<Cours> getCours(final int id) {

        return coursRepository.findById(id);
    }

    /**
     * Service pour créer un cours.
     *
     * @param cours Cours : cours à créer
     *
     * @return Cours : le cours créé
     */
    public Cours createCours(final Cours cours) {

        if (cours.getIdCours() != null) {
            throw new ModelException("L'identifiant du cours doit être nul");
        }
        return coursRepository.save(cours);
    }

    /**
     * Service pour modifier un cours.
     *
     * @param cours Cours : cours à modifier
     *
     * @return Cours : le cours modifié
     */
    public Cours updateCours(final Cours cours) {

        if (cours.getIdCours() == null) {
            throw new ModelException("L'identifiant du cours doit être non nul");
        }
        return coursRepository.save(cours);
    }

    /**
     * Service pour supprimer un cours selon son identifiant.
     *
     * @param id int : identifiant du cours à supprimer
     */
    public void deleteCours(final int id) {

        coursRepository.deleteById(id);
    }
}
