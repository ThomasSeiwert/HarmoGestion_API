package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.exceptions.ModelException;
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

    /**
     * Repository des instruments
     */
    private final InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentService(final InstrumentRepository instrumentRepository) {

        this.instrumentRepository = instrumentRepository;
    }

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
     * Enregistrement d'un nouvel instrument.
     *
     * @param instrument l'instrument à créer
     *
     * @return l'instrument après création
     */
    public Instrument createInstrument(Instrument instrument) {

        if (instrument.getIdInstrument() != null) {
            throw new ModelException("L'identifiant doit être nul");
        }
        return instrumentRepository.save(instrument);
    }

    /**
     * Modification d'un instrument.
     *
     * @param instrument l'instrument à modifier
     *
     * @return l'instrument après modification
     */
    public Instrument updateInstrument(Instrument instrument) {

        if (instrument.getIdInstrument() == null) {
            throw new ModelException("L'identifiant doit être non nul");
        }
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
