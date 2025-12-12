package com.gitlab.rmarzec.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactoryChrome {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public WebDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/java/com/gitlab/rmarzec/framework/utils/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        tlDriver.set(webDriver);
        return getDriver();
    }
}
