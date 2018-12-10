package com.roguewave.petclinic.vets.tests;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class VetResourcesITest {

    @Value("${baseUrl}")
    public String baseUrl;


    @Test
    public void testNavigationForward() {
        beforeTest();
        open(baseUrl + "/#!/welcome");

        System.out.println("");
        $(By.tagName("title")).shouldBe(exist);

        assertEquals(baseUrl + "/#!/welcome", url());


        String titleVeterinarians = "[title='veterinarians']";
        $(Selectors.byCssSelector(titleVeterinarians)).should(Condition.exist);
        $(Selectors.byCssSelector(titleVeterinarians)).click();

        $(By.tagName("title")).shouldBe(exist);

        assertEquals(baseUrl + "/#!/vets", url());
    }

    @Test
    public void testVetsAreLoaded() {
        beforeTest();
        open(baseUrl + "/#!/vets");
        sleep(5000);
        assertEquals(24, $$("[ng-repeat='vet in $ctrl.vetList']").size());
    }

    @Test
    public void testOwnersEditOwner() {
        beforeTest();
        open(baseUrl + "/#!/welcome");

        String ownersSelector = "Owners";

        $(Selectors.byText(ownersSelector)).should(Condition.exist);
        $(Selectors.byText(ownersSelector)).click();

        String allOwnersSelector = "All";

        $(Selectors.byText(allOwnersSelector)).should(Condition.exist);
        $(Selectors.byText(allOwnersSelector)).click();

        String GeorgeFranklin = "George Franklin";

        $(Selectors.byText(GeorgeFranklin)).should(Condition.exist);
        $(Selectors.byText(GeorgeFranklin)).click();

        open(baseUrl);
    }

    public void beforeTest() {
        //Temporary workaround for Zuul not wiring first request properly
        open(baseUrl);
        sleep(500);
        open(baseUrl + "/#!/vets");
        sleep(500);
        open(baseUrl + "/#!/owners");
        sleep(500);
        open(baseUrl);
    }
}
