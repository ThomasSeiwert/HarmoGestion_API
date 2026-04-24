package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstrumentController.class)
class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InstrumentService service;

    @Test
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de récupération de la liste des instruments")
    @Severity(SeverityLevel.CRITICAL)
    void getInstrumentsTest() throws Exception {
        mockMvc.perform(get("/instruments")).andExpect(status().isNoContent());
    }

    @Test
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de récupération d'un cours")
    @Severity(SeverityLevel.NORMAL)
    void getInstrumentTestKo() throws Exception {
        mockMvc.perform(get("/instrument/1")).andExpect(status().isBadRequest());
    }

    @Test
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de suppression d'un instrument")
    @Severity(SeverityLevel.NORMAL)
    void deleteInstrumentTest() throws Exception {
        mockMvc.perform(delete("/instrument/1")).andExpect(status().isBadRequest());
    }

    @Test
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de modification d'un instrument")
    @Severity(SeverityLevel.NORMAL)
    void updateInstrumentTest() throws Exception {
        mockMvc.perform(
                put("/instrument/1").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new Instrument()
                        ))).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de création d'un instrument non valide")
    @Severity(SeverityLevel.CRITICAL)
    @NullAndEmptySource
    @ValueSource(strings = {"a", "br",
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"})
    void createInstrumentTestKo(final String libelle) throws Exception {
        Instrument instrument = new Instrument(null, libelle);
        String json = new ObjectMapper().writeValueAsString(instrument);
        mockMvc.perform(post("/instrument")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @Description("Test unitaire du controller pour vérifier le statut de"
                 + " la requête de création d'un instrument valide")
    @Severity(SeverityLevel.CRITICAL)
    @ValueSource(strings = {"tria", "clarinette"})
    void createInstrumentTestOk(String libelle) throws Exception {
        Instrument instrument = new Instrument(null, libelle);
        String json = new ObjectMapper().writeValueAsString(instrument);
        mockMvc.perform(post("/instrument")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}