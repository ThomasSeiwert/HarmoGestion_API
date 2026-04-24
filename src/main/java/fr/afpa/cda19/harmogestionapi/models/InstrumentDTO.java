package fr.afpa.cda19.harmogestionapi.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 23/04/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentDTO {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Identifiant de l'instrument.
     */
    private Integer idInstrument;

    /**
     * Nom de l'instrument.
     */
    @NotBlank(message = "Un instrument doit avoir un nom")
    @Size(min = 3, max = 50,
            message = "Le nom de l'instrument doit faire entre trois "
                    + "et cinquante caractères de long")
    private String libelleInstrument;
}
