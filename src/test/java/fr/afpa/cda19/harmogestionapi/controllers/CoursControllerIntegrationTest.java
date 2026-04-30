package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Tests d'intégration Mock du controller des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 20/04/2026
 */
@SpringBootTest
@AutoConfigureMockMvc
class CoursControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Cours COURS = new Cours();

    @BeforeEach
    void initCours() {

        COURS.setDateCours(LocalDateTime.now().plusDays(2));
        COURS.setDureeCours((byte) 60);
        COURS.setEnseignant(new Membre(1, "Hendrix", "Jimmi",
                                       LocalDate.now(), null, null));
        COURS.setInstrument(new Instrument(1, "guitare"));
        COURS.setParticipants(new ArrayList<>());
        COURS.getParticipants().add(new Membre(2, "Seiwert", "Thomas",
                                               LocalDate.now(), null, null));
    }

    @Test
    @DisplayName("Test d'intégration du controller pour retourner la liste des cours")
    @Description("Envoi d'une requête pour retourner la liste des cours."
                 + " On s'attend à un statut 200 et à ce que le body contienne le cours.")
    @Severity(SeverityLevel.CRITICAL)
    void getProchainsCoursTest() throws Exception {

        mockMvc.perform(get("/cours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dureeCours", is(45)));
    }

    @Test
    @DisplayName("Test d'intégration du controller pour retourner le cours avec l'id 1")
    @Description("Envoi d'une requête pour retourner le cours avec l'id 1."
                 + " On s'attend à un statut 200 et à ce que le body contienne le cours.")
    @Severity(SeverityLevel.CRITICAL)
    void getCoursTestOk() throws Exception {

        mockMvc.perform(get("/cours/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dureeCours", is(45)));
    }

    @Test
    @DisplayName("Test d'intégration du controller pour retourner le cours avec l'id 5")
    @Description("Envoi d'une requête pour retourner le cours avec l'id 5."
                 + " On s'attend à un statut 400 car ce cours n'existe pas.")
    @Severity(SeverityLevel.CRITICAL)
    void getCoursTestKo() throws Exception {

        mockMvc.perform(get("/cours/5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test unitaire controller pour créer un cours valide avec un identifiant nul")
    @Description("Envoi d'une requête pour créer un cours valide avec un identifiant nul."
                 + " On s'attend à un statut 201.")
    @Severity(SeverityLevel.CRITICAL)
    void createCoursTestOk() throws Exception {

        COURS.setIdCours(null);
        final String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dureeCours", is(60)));
    }

    @Test
    @DisplayName("Test unitaire controller pour créer un cours avec un identifiant non nul et/ou invalide")
    @Description("Envoi d'une requête pour créer un cours avec un identifiant non nul et/ou invalide."
                 + " On s'attend à un statut 400.")
    @Severity(SeverityLevel.CRITICAL)
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

        COURS.setIdCours(null);
        json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test unitaire controller pour modifier un cours valide avec l'identifiant 1")
    @Description("Envoi d'une requête pour créer un cours valide avec l'identifiant 1."
            + " On s'attend à un statut 200.")
    @Severity(SeverityLevel.CRITICAL)
    void updateCoursTestOk() throws Exception {

        COURS.setIdCours(1);
        final String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dureeCours", is(60)));
    }

    @Test
    @DisplayName("Test unitaire controller pour modifier un cours non valide")
    @Description("Envoi d'une requête pour créer un cours l'identifiant 5, un cours non valide"
            + " et/ou un id nul. On s'attend à un statut 400.")
    @Severity(SeverityLevel.CRITICAL)
    void updateCoursTestKo() throws Exception {

        COURS.setIdCours(5);
        String json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/5")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        COURS.setIdCours(null);
        json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        COURS.setIdCours(1);
        json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        COURS.setDureeCours((byte) 10);
        json = new ObjectMapper().writeValueAsString(COURS);

        mockMvc.perform(put("/cours/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
