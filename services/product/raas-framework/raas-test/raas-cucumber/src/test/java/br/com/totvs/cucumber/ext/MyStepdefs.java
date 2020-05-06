package br.com.totvs.cucumber.ext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {

    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
//        Reporter.addStepLog("My test addStepLog message");
//        Reporter.addScenarioLog("This is scenario log");
    }

    @Given("I have {int} cukes in my bellies")
    public void I_have_cukes_in_my_bellies(int cukes) {
        System.out.format("Cukes: %n\n", cukes);
    }

    @Then("^I print$")
    public void iPrint() {
    }

    @When("^I login with credentials$")
    public void iLoginWithCredentials(DataTable table) {
    }

}
