package fr.afpa.cda19.harmogestionapi.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 23/04/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursDTO {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Identifiant.
     */
    private Integer idCours;

    /**
     * Date du cours.
     */
    @NotNull(message = "Le cours doit avoir une date")
    @FutureOrPresent(message = "Le cours doit être à une date future")
    private LocalDateTime dateCours;

    /**
     * Durée du cours (en min).
     */
    @Min(value = 30, message = "Le cours doit durer au moins 30 minutes")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes")
    private byte dureeCours;

    /**
     * Enseignant.
     */
    @NotNull(message = "Le cours doit avoir un enseignant")
    @Valid
    private Membre enseignant;

    /**
     * Instrument enseigné.
     */
    @NotNull(message = "Le cours doit concerner un instrument")
    @Valid
    private Instrument instrument;

    /**
     * Liste des participants.
     */
    @NotNull(message = "Le cours doit avoir des participants")
    @Size(min = 1, max = 15, message = "Le nombre de participants doit être "
            + "entre 1 et 15")
    private List<@Valid Membre> participants;
}
