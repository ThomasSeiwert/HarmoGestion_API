package fr.afpa.cda19.harmogestionapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void getAllCoursTest() throws Exception {

        mockMvc.perform(get("/cours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dureeCours", is(45)));
    }
}
