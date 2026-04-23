package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant un instrument.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 07/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instrument")
public class Instrument {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Identifiant de l'instrument.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instrument")
    private Integer idInstrument;

    /**
     * Nom de l'instrument.
     */
    @Column(name = "libelle_instrument")
    @NotBlank(message = "Un instrument doit avoir un nom")
    @Size(min = 3, max = 50,
          message = "Le nom de l'instrument doit faire entre trois "
                    + "et cinquante caractères de long")
    private String libelleInstrument;

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Méthode pour cloner un instrument DTO en instrument persistant.
     *
     * @param instrumentDTO instrument à cloner.
     *
     * @return instrument cloné.
     */
    public static Instrument clone(InstrumentDTO instrumentDTO) {

        Instrument instrumentClone = new Instrument();
        instrumentClone.setIdInstrument(instrumentDTO.getIdInstrument());
        instrumentClone.setLibelleInstrument(instrumentDTO.getLibelleInstrument());

        return instrumentClone;
    }
}
