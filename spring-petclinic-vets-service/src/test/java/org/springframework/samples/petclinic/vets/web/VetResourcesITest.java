package org.springframework.samples.petclinic.vets.web;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VetResourcesITest {



    @Test
    public void testNavigationForward() {
        open("http://localhost:8080");
        $(By.tagName("title")).shouldBe(exist);

        assertEquals("http://localhost:8080/#!/welcome", url());

        String cssSelector = "[title='veterinarians']";
        assertTrue($(By.cssSelector(cssSelector)).exists());
        $(By.cssSelector(cssSelector)).click();

        $(By.tagName("title")).shouldBe(exist);
        assertEquals("http://localhost:8080/#!/vets", url());
    }

    @Test
    public void testVetsAreLoaded() {
        open("http://localhost:8080/#!/vets");
        sleep(5000);
        assertEquals(6, $$("[ng-repeat='vet in $ctrl.vetList']").size());
    }

    @Test
    public void testOwnersEditOwner() {
        open("http://localhost:8080");

        String ownersSelector = "[class='dropdown']";
        assertTrue($(ownersSelector).exists());
        $(ownersSelector).click();

        String allOwnersSelector = "[ui-sref='owners']";
        assertTrue($(allOwnersSelector).exists());

        $(allOwnersSelector).click();
        sleep(3000);
        System.out.print("CURRENT URL: " + url());
        String GeorgeFranklin = "[href='#!/owners/details/2']";
        assertTrue($(GeorgeFranklin).exists());
        $(GeorgeFranklin).click();
    }


}
