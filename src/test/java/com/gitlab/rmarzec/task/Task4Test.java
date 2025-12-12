package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.model.YTTile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Task4Test {

    @Test
    public void Task4Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        
        //Lista kafelkow
        List<YTTile> ytTileList = new ArrayList<YTTile>();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get("https://www.youtube.com/");

        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[.//span[text()='Zaakceptuj wszystko']]")));
            if (acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
            }
        } catch (Exception e) {
            System.out.println("Ciasteczka zaakceptowane, przechodzę dalej.");
        }

        WebElement shortsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@title='Shorts' and @href]")));
        shortsButton.click();

        WebElement channel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#metapanel a")));
        System.out.println("Kanał: " + channel.getText());

        webDriver.navigate().back();

        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("yt-searchbox input")));
        search.sendKeys("Live");
        search.submit();

        List<WebElement> videosList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(
                "(//ytd-item-section-renderer/div[@id='contents']/ytd-video-renderer)[position() <= 12]"),11));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".yt-badge-shape__text")));

        for (WebElement video : videosList) {
            YTTile tile = new YTTile();
            tile.setTitle(wait.until(ExpectedConditions.visibilityOf(
                    video.findElement(By.xpath(".//*[@id='video-title']")))).getText());
            tile.setChannel(wait.until(ExpectedConditions.visibilityOf(
                    video.findElement(By.xpath(".//*[@id='channel-name' and @wrap-text]")))).getText());
            WebElement length = video.findElement(By.xpath(
                    ".//*[@class='yt-badge-shape__text' and (contains(text(),':') or text()='NA ŻYWO')]"));
            tile.setLength(length.getText().equals("NA ŻYWO") ? "live" : length.getText());
            ytTileList.add(tile);
        }

        System.out.println("Lista fimów:");
        for (YTTile tile : ytTileList) {
            if (!tile.getLength().equals("live")) {
                System.out.println("- " + tile.getTitle() + " -> "+tile.getChannel() + " -> " + tile.getLength());
            }
        }

        webDriver.quit();
    }
}
