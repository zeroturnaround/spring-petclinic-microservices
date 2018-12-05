package com.roguewave.petclinic.vets.tests;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeAll;
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



@ExtendWith(SpringExtension.class)
@SpringBootTest
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
    public void testOwnersDetails() {
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

    @Test
    public void testOwnersEdit() {
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

        String editOwner = "Edit Owner";
        $(Selectors.byText(editOwner)).should(Condition.exist);
        $(Selectors.byText(editOwner)).click();

        String submit = "Submit";
        $(Selectors.byText(submit)).should(Condition.exist);
        $(Selectors.byText(submit)).click();

        open(baseUrl);
    }

    public void beforeTest() {
        //Temporary workaround for Zuul not wiring first request properly
        open(baseUrl);
        sleep(5000);
        open(baseUrl + "/#!/vets");
        sleep(5000);
        open(baseUrl + "/#!/owners");
        sleep(5000);
        open(baseUrl);
    }

    @Test
    public void testVetDetails(){
        open(baseUrl + "/#!/vets");

        String JamesCarter = "James Carter";
        findAndOpenVet(JamesCarter);

        open(baseUrl + "/#!/vets");

        findAndOpenVet("Henry Stevens");
    }

    @Test
    public void testVetEditName() {
        open(baseUrl + "/#!/vets");

        findAndOpenVet("Helen Leary");
        $(Selectors.byText("Edit Vet")).click();

        $(Selectors.byName("firstName")).setValue("Helena");
        $(Selectors.byText("Save")).click();

        findAndOpenVet("Helena Leary");
        $(Selectors.byText("Edit Vet")).click();

        $(Selectors.byName("firstName")).setValue("Helen");
        $(Selectors.byText("Save")).click();
    }

    @Test
    public void testVetEditSpecialty() {
        open(baseUrl + "/#!/vets");

        findAndOpenVet("Henry Stevens");
        $(Selectors.byText("Edit Vet")).click();

        $(Selectors.byName("specialty")).setValue("radiology surgery");
        $(Selectors.byText("Save")).click();

        findAndOpenVet("Henry Stevens");
        $(Selectors.byText("radiology")).should(Condition.exist);
        $(Selectors.byText("surgery")).should(Condition.exist);
        $(Selectors.byText("Edit Vet")).click();

        $(Selectors.byName("specialty")).setValue("radiology");
        $(Selectors.byText("Save")).click();
    }



    private void findAndOpenVet(String vetName) {
        $(Selectors.byText(vetName)).should(Condition.exist);
        $(Selectors.byText(vetName)).click();

        $(Selectors.byText("Veterinarians")).should(Condition.exist);
        $(Selectors.byText(vetName)).should(Condition.exist);
    }
}
