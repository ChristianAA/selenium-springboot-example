package com.example.selenium.steps;

import com.example.selenium.pages.SearchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchSteps {

    @Autowired
    private SearchPage searchPage;

    @Given("^a user typing \"([^\"]*)\" in the search input$")
    public void aUserTypingInTheSearchInput(String searchValue) {
        searchPage.inputSearch(searchValue);
    }

    @When("^presses the search button$")
    public void pressesTheSearchButton() {
        searchPage.pressSearchButton();
    }
}
