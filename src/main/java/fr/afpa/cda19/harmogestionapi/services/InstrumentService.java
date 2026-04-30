package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.repositories.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service de liaison entre les contrôleurs et le repository.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 07/04/2026
 */
@Service
public class InstrumentService {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Repository des instruments
     */
    private final InstrumentRepository instrumentRepository;

    //--------------------------------------------------------------------------
    // Constructeurs
    //--------------------------------------------------------------------------

    @Autowired
    public InstrumentService(final InstrumentRepository instrumentRepository) {

        this.instrumentRepository = instrumentRepository;
    }

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Récupèration de la liste des instruments.
     *
     * @return la liste des instruments
     */
    public Iterable<Instrument> getInstruments() {

        return instrumentRepository.findAll();
    }

    /**
     * Récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument recherché
     *
     * @return l'éventuel instrument correspondant à l'identifiant
     */
    public Optional<Instrument> getInstrument(int id) {

        return instrumentRepository.findById(id);
    }

    /**
     * Enregistrement/mofification d'un instrument.
     *
     * @param instrument l'instrument à créer/modifier
     *
     * @return l'instrument après création/modification
     */
    public Instrument saveInstrument(Instrument instrument) {

        return instrumentRepository.save(instrument);
    }

    /**
     * Suppression d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à supprimer
     */
    public void deleteInstrument(int id) {

        instrumentRepository.deleteById(id);
    }
}
