package com.example.selenium.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "html:results/smth", "json:results/cucumber.json", "junit:results/cucumber.xml"},
        glue = "com.example.selenium",
        tags = "not @wip")

public class TestRunner {

}
