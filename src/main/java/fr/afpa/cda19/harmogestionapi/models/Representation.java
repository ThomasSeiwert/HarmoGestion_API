package fr.afpa.cda19.harmogestionapi.models;

import fr.afpa.cda19.harmogestionapi.dto.RepresentationDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
 * Model des représentations.
 *
 * @author Cyril
 * @version 0.0.1
 * @since 10/04/2026
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "representation")
public class Representation {

    //--------------------------------------------------------------------------
    // Attributs
    //--------------------------------------------------------------------------

    /**
     * Identifiant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_representation")
    private Integer idRepresentation;

    /**
     * Nom de la représentation.
     */
    @Column(name = "nom_representation")
    @NotNull(message = "La représentation doit avoir un nom")
    @Size(min = 3, max = 50,
            message = "Le nom de la représentation doit faire entre trois "
                    + "et cinquante caractères de long")
    private String nomRepresentation;

    /**
     * Date et heure de la représentation.
     */
    @Column(name = "date_representation")
    @NotNull(message = "La représentation doit avoir une date")
    @FutureOrPresent(message = "La représentation doit être à une date future")
    private LocalDateTime dateRepresentation;

    /**
     * Lieu de la représentation.
     */
    @Column(name = "lieu_representation")
    @NotNull(message = "La représentation doit avoir un lieu")
    @Size(min = 3, max = 50,
            message = "Le lieu de la représentation doit faire entre trois "
                    + "et cinquante caractères de long")
    private String lieuRepresentation;

    /**
     * Liste des membres participants.
     */
    @ManyToMany
    @JoinTable(
            name = "participer_representation",
            joinColumns = @JoinColumn(name = "id_representation"),
            inverseJoinColumns = @JoinColumn(name = "id_membre")
    )
    @NotNull(message = "La représentation doit avoir des participants")
    @Size(min = 1, message = "Il doit y avoir au moins un participant")
    private List<@Valid Membre> participants;

    /**
     * Liste des instruments joués.
     */
    @ManyToMany
    @JoinTable(
            name = "instruments_representation",
            joinColumns = @JoinColumn(name = "id_representation"),
            inverseJoinColumns = @JoinColumn(name = "id_instrument")
    )
    @NotNull(message = "La représentation doit avoir des instruments")
    @Size(min = 1, message = "Il doit y avoir au moins un instrument")
    private List<@Valid Instrument> instruments;

    //--------------------------------------------------------------------------
    // Méthodes
    //--------------------------------------------------------------------------

    /**
     * Méthode pour cloner une représentation DTO en représentation persistante.
     *
     * @param representationDTO représentation à cloner.
     *
     * @return représentation clonée.
     */
    public static Representation clone(final RepresentationDTO representationDTO) {

        Representation representationClone = new Representation();
        representationClone.setIdRepresentation(representationDTO.getIdRepresentation());
        representationClone.setNomRepresentation(representationDTO.getNomRepresentation());
        representationClone.setDateRepresentation(representationDTO.getDateRepresentation());
        representationClone.setLieuRepresentation(representationClone.getLieuRepresentation());
        representationClone.setParticipants(representationDTO.getParticipants());
        representationClone.setInstruments(representationDTO.getInstruments());

        return representationClone;
    }
}
