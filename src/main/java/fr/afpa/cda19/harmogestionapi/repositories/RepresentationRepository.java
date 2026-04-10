package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Representation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les représentations.
 *
 * Fournit les opérations CRUD via CrudRepository.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@Repository
public interface RepresentationRepository extends CrudRepository<Representation, Integer> {
}
