package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Task2Test {
    @Test
    public void Task2Test() {
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get("https://pl.wikipedia.org/wiki/Wiki");

        WebElement languageButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("p-lang-btn")));
        languageButton.click();

        List<WebElement> allLanguagesList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".uls-language-list li")));

        Map<String, WebElement> uniqueElementsMap = new HashMap<>();

        for (WebElement element : allLanguagesList) {
            String text = element.getText();
            // This ensures only the first WebElement for a given text is stored, removing duplicates.
            uniqueElementsMap.putIfAbsent(text, element);
        }

        // 2. Convert the Map values (the unique WebElements) into a List.
        List<WebElement> uniqueLanguagesList = new ArrayList<>(uniqueElementsMap.values());

        System.out.println("Lista języków:");
        for (WebElement language : uniqueLanguagesList) {
            String languageName = language.getText();
            String displayUrl = language.findElement(By.cssSelector("a")).getAttribute("href");
            System.out.print(languageName);
            System.out.println(languageName.equals("English") ? " -> " + displayUrl : "");
        }

        webDriver.close();
    }
}
