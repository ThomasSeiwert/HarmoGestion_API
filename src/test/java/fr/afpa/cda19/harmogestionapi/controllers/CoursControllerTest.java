package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.services.CoursService;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockitoBean
    private CoursService coursService;

    @Test
    @DisplayName("Test unitaire controller pour retourner la liste des cours")
    @Description("Envoi d'une requête pour retourner la liste des cours."
                 + " On s'attend à un statut 404.")
    @Severity(SeverityLevel.CRITICAL)
    void getAllCoursTest() throws Exception {

        mockMvc.perform(get("/cours")).andExpect(status().isNotFound());
    }
}
