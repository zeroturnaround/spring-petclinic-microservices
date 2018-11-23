package com.roguewave.petclinic.vets.tests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class VetResourcesITest {

    @Value("${baseUrl}")
    public String baseUrl;


    @Test
    public void testNavigationForward() {
        open(baseUrl);

        System.out.println("");
        $(By.tagName("title")).shouldBe(exist);

        assertEquals(baseUrl + "/#!/welcome", url());

        String cssSelector = "[title='veterinarians']";
        assertTrue($(By.cssSelector(cssSelector)).exists());
        $(By.cssSelector(cssSelector)).click();

        $(By.tagName("title")).shouldBe(exist);
        assertEquals(baseUrl + "/#!/vets", url());
    }

    @Test
    public void testVetsAreLoaded() {
        open(baseUrl + "/#!/vets");
        sleep(5000);
        assertEquals(6, $$("[ng-repeat='vet in $ctrl.vetList']").size());
    }

    @Test
    public void testOwnersEditOwner() {
        open(baseUrl);

        String ownersSelector = "[class='dropdown']";
        assertTrue($(ownersSelector).exists());
        $(ownersSelector).click();

        String allOwnersSelector = "[ui-sref='owners']";
        assertTrue($(allOwnersSelector).exists());

        $(allOwnersSelector).click();
        sleep(3000);
        String GeorgeFranklin = "[href='#!/owners/details/2']";
        assertTrue($(GeorgeFranklin).exists());
        $(GeorgeFranklin).click();
    }

}
