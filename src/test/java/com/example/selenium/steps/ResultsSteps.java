package com.example.selenium.steps;

import com.example.selenium.pages.ResultsPage;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class ResultsSteps {

    @Autowired
    private ResultsPage resultsPage;

    @Then("^results are displayed$")
    public void resultsAreDisplayed() {
        resultsPage.assertResultsArePresent();
    }
}
