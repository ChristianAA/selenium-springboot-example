package com.example.selenium.steps;

import com.example.selenium.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeSteps {

    @Autowired
    private HomePage homePage;

    @Given("^I search \"([^\"]*)\" in the search input of the home page$")
    public void iSearchInTheSearchInputOfTheHomePage(String search) {
        homePage.inputSearch(search);
    }

    @When("^I press the search button in the home page$")
    public void iPressTheSearchButtonInTheHomePage() {
        homePage.pressSearchButton();
    }
}
