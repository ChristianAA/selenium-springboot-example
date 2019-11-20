package com.example.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

@Component
public class HomePage implements BasePage {

    @FindBy(how = How.CSS, using = "#search_form_input_homepage")
    private WebElement searchInput;

    @FindBy(how = How.CSS, using = "#search_button_homepage")
    private WebElement searchButton;

    public void inputSearch(String search){
        searchInput.sendKeys(search);
    }

    public void pressSearchButton(){
        searchButton.click();
    }
}