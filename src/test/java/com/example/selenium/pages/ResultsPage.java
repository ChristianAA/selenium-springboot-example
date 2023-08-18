package com.example.selenium.pages;

import com.example.selenium.helpers.VisibilityHelper;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultsPage implements BasePage {

    private static final String RESULTS_LOCATOR = "[data-testid='mainline'] [data-testid='result']";

    @Autowired
    private VisibilityHelper visibilityHelper;


    public void assertResultsArePresent(){
        visibilityHelper.waitForPresenceOf(By.cssSelector(RESULTS_LOCATOR));
    }
}