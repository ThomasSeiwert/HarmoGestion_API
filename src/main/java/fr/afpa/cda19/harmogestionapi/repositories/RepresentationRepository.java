package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Representation;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les représentations.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@Repository
public interface RepresentationRepository extends CrudRepository<Representation, Integer> {

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Retourne les prochaines représentations dans la BDD.
     *
     * @return prochaines représentations.
     */
    @NativeQuery("SELECT * "
            + "FROM representation "
            + "WHERE representation.date_representation > NOW()")
    Iterable<Representation> findAllInFuture();
}
