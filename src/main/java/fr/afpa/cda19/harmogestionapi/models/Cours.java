package fr.afpa.cda19.harmogestionapi.models;

import fr.afpa.cda19.harmogestionapi.dto.CoursDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
 * Model des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 08/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cours")
public class Cours {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Identifiant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private Integer idCours;

    /**
     * Date du cours.
     */
    @Column(name = "date_cours")
    @NotNull(message = "Le cours doit avoir une date")
    @FutureOrPresent(message = "Le cours doit être à une date future")
    private LocalDateTime dateCours;

    /**
     * Durée du cours (en min).
     */
    @Column(name = "duree_cours")
    @Min(value = 30, message = "Le cours doit durer au moins 30 minutes")
    @Max(value = 120, message = "Le cours doit durer au maximum 120 minutes")
    private byte dureeCours;

    /**
     * Enseignant.
     */
    @ManyToOne
    @JoinColumn(name = "id_membre_enseignant")
    @NotNull(message = "Le cours doit avoir un enseignant")
    @Valid
    private Membre enseignant;

    /**
     * Instrument enseigné.
     */
    @ManyToOne
    @JoinColumn(name = "id_instrument")
    @NotNull(message = "Le cours doit concerner un instrument")
    @Valid
    private Instrument instrument;

    /**
     * Liste des participants.
     */
    @ManyToMany
    @JoinTable(
            name = "participer_cours",
            joinColumns = @JoinColumn(name = "id_cours"),
            inverseJoinColumns = @JoinColumn(name = "id_membre_apprenant")
    )
    @NotNull(message = "Le cours doit avoir des participants")
    @Size(min = 1, max = 15, message = "Le nombre de participants doit être "
                                       + "entre 1 et 15")
    private List<@Valid Membre> participants;

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Méthode pour cloner un cours DTO en cours persistant.
     *
     * @param coursDTO cours à cloner.
     *
     * @return cours cloné.
     */
    public static Cours clone(final CoursDTO coursDTO) {

        Cours coursClone = new Cours();
        coursClone.setIdCours(coursDTO.getIdCours());
        coursClone.setDateCours(coursDTO.getDateCours());
        coursClone.setDureeCours(coursDTO.getDureeCours());
        coursClone.setEnseignant(coursDTO.getEnseignant());
        coursClone.setInstrument(coursDTO.getInstrument());
        coursClone.setParticipants(coursDTO.getParticipants());

        return coursClone;
    }
}
