package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @Column(name = "nom_representation", nullable = false)
    @NotNull(message = "La représentation doit avoir un nom")
    private String nomRepresentation;

    /**
     * Date et heure de la représentation.
     */
    @Column(name = "date_representation", nullable = false)
    @NotNull(message = "La représentation doit avoir une date")
    private LocalDateTime dateRepresentation;

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
}
