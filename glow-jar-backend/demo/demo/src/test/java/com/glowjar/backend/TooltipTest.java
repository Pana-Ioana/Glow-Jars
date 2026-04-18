package com.glowjar.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TooltipTest extends BaseTest {

    @Test
    public void createJarAndVerifyTooltipTest() throws InterruptedException {
        driver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        WebElement createJarButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[self::button or self::a][contains(.,'Creează jar')]")
                )
        );
        createJarButton.click();

        wait.until(ExpectedConditions.urlContains("/customize-jar"));

        WebElement styleField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(.,'Stil / vibe') or contains(.,'Stil')]/following::input[1]")
                )
        );
        styleField.click();
        styleField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        styleField.sendKeys(Keys.DELETE);
        styleField.sendKeys("Elegant gold minimal");

        WebElement materialDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(.,'Material')]/ancestor::*[contains(@class,'v-field')][1]")
                )
        );
        materialDropdown.click();

        WebElement aurOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='option' and contains(.,'Aur')]")
                )
        );
        aurOption.click();

        WebElement packageDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(.,'Pachet')]/ancestor::*[contains(@class,'v-field')][1]")
                )
        );
        packageDropdown.click();

        WebElement packageOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='option' and contains(.,'10-12 piese')]")
                )
        );
        packageOption.click();

        WebElement ringSizeDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[contains(.,'Mărime inele') or contains(.,'Marime inele')]/ancestor::*[contains(@class,'v-field')][1]")
                )
        );
        ringSizeDropdown.click();

        WebElement mediumOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='option' and contains(.,'Mediu')]")
                )
        );
        mediumOption.click();

        WebElement notesField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea")
                )
        );
        notesField.click();
        notesField.sendKeys("Fără instrucțiuni speciale momentan.");

        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[self::button or @role='button'][contains(.,'Adaugă în coș') or contains(.,'Adauga in cos')]")
                )
        );
        actions.moveToElement(addToCartButton).click().perform();

        Thread.sleep(2000);

        boolean messageFound = driver.getPageSource().contains("adăugat")
                || driver.getPageSource().contains("Adăugat")
                || driver.getPageSource().contains("coș")
                || driver.getPageSource().contains("cos");

        Assertions.assertTrue(messageFound, "Confirmation message was not detected.");
    }
}