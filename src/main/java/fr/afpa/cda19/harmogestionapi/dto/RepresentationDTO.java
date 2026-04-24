package fr.afpa.cda19.harmogestionapi.dto;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
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
 * @since 24/04/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentationDTO {

    /**
     * Identifiant.
     */
    private Integer idRepresentation;

    /**
     * Nom de la représentation.
     */
    @NotNull(message = "La représentation doit avoir un nom")
    @Size(min = 3, max = 50,
            message = "Le nom de la représentation doit faire entre trois "
                    + "et cinquante caractères de long")
    private String nomRepresentation;

    /**
     * Date et heure de la représentation.
     */
    @NotNull(message = "La représentation doit avoir une date")
    @FutureOrPresent(message = "La représentation doit être à une date future")
    private LocalDateTime dateRepresentation;

    /**
     * Liste des membres participants.
     */
    @NotNull(message = "La représentation doit avoir des participants")
    @Size(min = 1, message = "Il doit y avoir au moins un participant")
    private List<@Valid Membre> participants;

    /**
     * Liste des instruments joués.
     */
    @NotNull(message = "La représentation doit avoir des instruments")
    @Size(min = 1, message = "Il doit y avoir au moins un instrument")
    private List<@Valid Instrument> instruments;
}
