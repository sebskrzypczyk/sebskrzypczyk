package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class Task3Test {

    @Test
    public void Task3Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptGoogleCookiesButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("L2AGLb")));
        if (acceptGoogleCookiesButton.isDisplayed()) {
            acceptGoogleCookiesButton.click();
        }

        WebElement searchTextarea = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".SDkEP textarea")));
        searchTextarea.sendKeys("W3Schools");

        WebElement luckyButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".aajZCb .RNmpXc")));
        luckyButton.click();

        String url = "https://www.w3schools.com/tags/tag_select.asp";
        if (!webDriver.getCurrentUrl().equals(url)) {
            webDriver.get(url);
        }

        WebElement iframeCookies = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fast-cmp-iframe")));
        webDriver.switchTo().frame(iframeCookies);

        WebElement acceptW3schoolsCookiesButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#fast-cmp-container .fast-cmp-button-primary")));
        if (acceptW3schoolsCookiesButton.isDisplayed()) {
            acceptW3schoolsCookiesButton.click();
        }

        webDriver.switchTo().defaultContent();

        WebElement tryItYourselfButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Try it Yourself')]")));
        tryItYourselfButton.click();

        Object[] windowHandles=webDriver.getWindowHandles().toArray();
        webDriver.switchTo().window((String) windowHandles[1]);

        WebElement iframeResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iframeResult")));
        webDriver.switchTo().frame(iframeResult);

        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
        System.out.print("Nagłówek: " + title.getText());

        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("cars")));
        Select select = new Select(selectElement);
        select.selectByValue("opel");
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println("Selected option: " + selectedOption.getText() + ", " +
                selectedOption.getAttribute("value"));

        webDriver.close();
    }
}
