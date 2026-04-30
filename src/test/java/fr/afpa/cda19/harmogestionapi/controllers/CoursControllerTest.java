package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests unitaires Mock du controller des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 08/04/2026
 */
@WebMvcTest(controllers = CoursController.class)
class CoursControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Cours COURS = new Cours();

    @MockitoBean
    private CoursService coursService;

    @BeforeAll
    static void initCours() {

        COURS.setDateCours(LocalDateTime.now().plusDays(1));
        COURS.setDureeCours((byte) 60);
        COURS.setEnseignant(new Membre(1, "Hendrix", "Jimmi",
                                       LocalDate.now().minusDays(1),
                                       null, null));
        COURS.setInstrument(new Instrument(1, "guitare"));
        COURS.setParticipants(new ArrayList<>());
        COURS.getParticipants().add(new Membre(2, "Seiwert", "Thomas",
                                               LocalDate.now().minusDays(1),
                                               null, null));
    }

    @Test
    @DisplayName("Test unitaire controller pour retourner la liste des cours")
    @Description("Envoi d'une requête pour retourner la liste des cours."
                 + " On s'attend à un statut 404 car BDD vide.")
    @Severity(SeverityLevel.CRITICAL)
    void getAllCoursTest() throws Exception {

        mockMvc.perform(get("/cours")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test unitaire controller pour retourner un cours")
    @Description("Envoi d'une requête pour retourner un cours avec un identifiant 1."
                 + " On s'attend à un statut 400 car BDD vide.")
    @Severity(SeverityLevel.NORMAL)
    void getCoursTest() throws Exception {

        mockMvc.perform(get("/cours/1")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test unitaire controller pour créer un cours avec un identifiant nul")
    @Description("Envoi d'une requête pour créer un cours avec un identifiant nul."
                 + " On s'attend à un statut 201.")
    @Severity(SeverityLevel.CRITICAL)
    void createCoursTestOk() throws Exception {

        COURS.setIdCours(null);
        final String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test unitaire controller pour créer un cours avec un identifiant non nul ou invalide")
    @Description("Envoi d'une requête pour créer un cours avec un identifiant non nul ou invalide."
                 + " On s'attend à un statut 400.")
    @Severity(SeverityLevel.NORMAL)
    void createCoursTestKo() throws Exception {

        COURS.setIdCours(1);
        String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        COURS.setDureeCours((byte) 10);
        json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test unitaire controller pour modifier un cours")
    @Description("Envoi d'une requête pour modifier le cours un identifiant 1."
                 + " On s'attend à un statut 400 car aucun cours dans la BDD.")
    @Severity(SeverityLevel.NORMAL)
    void updateCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test unitaire controller pour supprimer un cours")
    @Description("Envoi d'une requête pour supprimer le cours un identifiant 1."
                 + " On s'attend à un statut 400 car aucun cours dans la BDD.")
    @Severity(SeverityLevel.NORMAL)
    void deleteCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(delete("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
