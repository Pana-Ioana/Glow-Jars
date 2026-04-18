package com.glowjar.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class SearchTest extends BaseTest {
    @Test
    public void searchProductTest() throws InterruptedException {
        driver.get(baseUrl);

        WebElement searchField = driver.findElement(
                By.xpath("//input[contains(@placeholder,'Caută')]")
        );
        searchField.sendKeys("gold");
        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(3000);

        WebElement resultsText = driver.findElement(
                By.xpath("//*[contains(text(),'Rezultate pentru')]")
        );

        Assertions.assertTrue(
                resultsText.isDisplayed(),
                "Search results were not displayed."
        );
    }
}
