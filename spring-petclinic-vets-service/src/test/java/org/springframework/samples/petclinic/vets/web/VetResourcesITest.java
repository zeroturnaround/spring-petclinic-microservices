package org.springframework.samples.petclinic.vets.web;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VetResourcesITest {

    WebDriver currentWebdrive  = WebDriverRunner.getWebDriver();;

    @Test
    public void testNavigationForward() {
        open("http://localhost:8080");

        assertEquals("http://localhost:8080/#!/welcome", currentWebdrive.getCurrentUrl());

        String cssSelector = "[title='veterinarians']";
        assertTrue($(cssSelector).exists());
        $(cssSelector).click();

        assertEquals("http://localhost:8080/#!/vets", currentWebdrive.getCurrentUrl());
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
        System.out.print("CURRENT URL: " + currentWebdrive.getCurrentUrl());
        String GeorgeFranklin = "[href='#!/owners/details/2']";
        assertTrue($(GeorgeFranklin).exists());
        $(GeorgeFranklin).click();
    }


}
