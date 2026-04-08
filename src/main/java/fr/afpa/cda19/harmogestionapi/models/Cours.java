package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Model des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 08/04/2026
 */
@Data
@Entity
@Table(name = "Cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cours")
    private Integer idCours;

    @Column(name = "date_cours")
    private LocalDateTime dateCours;

    @Column(name = "duree_cours")
    private byte dureeCours;

//    @OneToMany
//    @JoinColumn(name = "id_membre_enseignant")
//    private Membre enseignant;
//
//    @OneToMany
//    @JoinColumn(name = "id_instrument")
//    private Instrument instrument;
//
//    @ManyToMany
//    @JoinTable(
//            name = "Participer_Cours",
//            joinColumns = @JoinColumn(name = "id_membre_apprenant"),
//            inverseJoinColumns = @JoinColumn(name = "id_cours")
//    )
//    private ArrayList<Membre> participants;
}
