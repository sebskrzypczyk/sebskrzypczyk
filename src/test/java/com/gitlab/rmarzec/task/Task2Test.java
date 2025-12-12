package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class Task2Test {
    @Test
    public void Task2Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get("https://pl.wikipedia.org/wiki/Wiki");

        WebElement languageButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("p-lang-btn")));
        languageButton.click();

        List<WebElement> allLanguagesList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".uls-language-list li")));

        List<WebElement> uniqueLanguagesList = allLanguagesList.stream()
                .collect(Collectors.toMap(
                        link -> link.getAttribute("href"),
                        link -> link,
                        (k1, k2) -> k1,
                        java.util.LinkedHashMap::new
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        System.out.println("Lista języków:");
        for (WebElement language : uniqueLanguagesList) {
            String languageName = language.getText();
            String displayUrl = language.getAttribute("href");
            System.out.print(" - " + languageName);
            System.out.println(languageName.equals("English") ? " -> " + displayUrl : "");
        }

        webDriver.close();
    }
}
