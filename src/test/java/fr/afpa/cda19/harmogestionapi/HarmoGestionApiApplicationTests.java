package fr.afpa.cda19.harmogestionapi;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HarmoGestionApiApplicationTests {

	@Test
    @Description("Test de vérification du contexte de l'application")
    @Severity(SeverityLevel.BLOCKER)
	void contextLoads() {
	}

}
