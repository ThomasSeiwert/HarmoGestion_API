package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
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
    void getCoursTest() throws Exception {

        mockMvc.perform(get("/cours/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dureeCours", is(45)));
    }
}
