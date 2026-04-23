package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository des cours.
 */
@Repository
public interface CoursRepository extends CrudRepository<Cours, Integer> {

    /**
     * Retourne les prochains cours dans la BDD.
     *
     * @return prochains cours.
     */
    @NativeQuery("SELECT * "
            + "FROM cours "
            + "WHERE cours.date_cours > NOW()")
    Iterable<Cours> findAllInFuture();
}
