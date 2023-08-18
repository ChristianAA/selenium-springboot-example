package com.example.selenium.runners;

import static java.lang.Boolean.TRUE;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import io.cucumber.java.Scenario;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.bonigarcia.wdm.WebDriverManager;

@Component
public class Hook {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Hook.class);

    @Value("${selenium.browser}")
    private String browser;

    @Value("${selenium.browser.headless}")
    private Boolean headless;

    @Value("${selenium.browser.remote}")
    private String remote;

    @Value("${target.application.baseUrl}")
    private String baseUrl;

    private WebDriver driver;

    private WebDriverWait wait;

    @PostConstruct
    public void initialize() {

        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isDriverLoaded()) {
                LOGGER.info("Shutdown signal detected: Closing opened drivers");
                closeDriver();
                LOGGER.info("Opened drivers closed");
            }
        }));
        // --
    }

    private boolean isDriverLoaded() {
        return driver != null;
    }

    public WebDriver getDriver() {
        if (isEmpty(driver)) {
            initialiseDriver();
        }
        return driver;
    }

    public WebDriverWait getWait() {
        if (isEmpty(wait)) {
            initialiseDriver();
        }
        return wait;
    }

    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    public void closeDriver() {
        if (driver == null) {
            return;
        }

        driver.quit();
        driver = null;
    }

    private void initialiseDriver() {

        // Prevent mem leak
        if (!isEmpty(driver)) {
            closeDriver();
        }

        // Disable driver log output
        System.setProperty("webdriver.chrome.silentOutput", "true");

        if ("chrome".equals(browser)) {
            setChromeDriver();
        } else if ("firefox".equals(browser)) {
            setFirefoxDriver();
        }

        // Navigate
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        goToBaseUrl();
    }

    private void setChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");

        // Headless mode
        if (TRUE.equals(headless)) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--window-size=1920,1080");
        }

        // Remote mode
        if ("grid".equals(remote)) {

            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            } catch (MalformedURLException e) {
                LOGGER.error("Error", e);
            }
        } else {
            driver = new ChromeDriver(chromeOptions);
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void setFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxBinary firefoxBinary = new FirefoxBinary();

        // Headless mode
        if (TRUE.equals(headless)) {
            firefoxBinary.addCommandLineOptions("--headless");
            firefoxOptions.addArguments("--width=1920");
            firefoxOptions.addArguments("--height=1080");
        }

        firefoxOptions.setBinary(firefoxBinary);

        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void goToBaseUrl() {
        driver.navigate().to(baseUrl);
    }

}