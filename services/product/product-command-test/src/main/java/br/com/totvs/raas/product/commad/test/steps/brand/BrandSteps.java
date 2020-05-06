package br.com.totvs.raas.product.commad.test.steps.brand;

import br.com.totvs.raas.core.test.context.Result;
import br.com.totvs.raas.core.test.steps.IdentifierContext;
import br.com.totvs.raas.core.test.steps.ResultContext;
import br.com.totvs.raas.product.commad.test.port.adapter.persistence.BrandRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static br.com.totvs.hamcrest.ext.Assert.assertThat;
import static br.com.totvs.hamcrest.ext.matcher.Matchers.allOf;
import static br.com.totvs.hamcrest.ext.matcher.Matchers.hasEntry;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BrandSteps {

    @Autowired
    private IdentifierContext identifierContext;

    @Autowired
    private BrandAdapter brandAdapter;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandContext brandContext;

    @Autowired
    private ResultContext resultContext;

    @Given("I have a {status} Brand with {value} name")
    public void givenIHaveABrandWith(Boolean status, String name) {
        String id = brandRepository.save(name, status);

        identifierContext.set(id);
    }

    @When("I save a {status} Brand with {value} name")
    public void whenISaveABrandWith(Boolean status, String name) {
        resultContext.set(brandAdapter.save(name, status));
    }

    @When("I change the Brand status to {status} and the name to {value}")
    public void whenIChangeTheBrand(Boolean status, String name) {
        resultContext.set(brandAdapter.change(identifierContext.get(), name, status));
    }

    @When("I delete the Brand")
    public void whenIDeleteTheBrand() {
        resultContext.set(brandAdapter.delete(identifierContext.get()));
    }

    @Then("It should have successfully saved a brand")
    public void thenItShouldHaveSuccessfullySavedaBrand() {
        Result result = resultContext.getSuccess();

        assertThat(((Map<String, Object>) result.getValue()),
                allOf(hasEntry("id", Matchers.notNullValue())));

        identifierContext.set(result.getId());
    }

    @Then("I should have a brand created with {status} status and {value} name")
    public void thenIShouldHaveABrandCreatedWith(Boolean status, String name) {
        Map<String, Object> brand = brandRepository.findCreatedBy(identifierContext.get());

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                                hasEntry("activated", (Object) status)));
    }

    @Then("I should have a brand changed with {status} status and {value} name")
    public void thenIShouldHaveAnAlteredBrandWith(Boolean status, String name) {
        Map<String, Object> brand = brandRepository.findChangedBy(identifierContext.get());

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                                hasEntry("activated", (Object) status)));
    }

    @Then("I should be notified with the brand created event containing {status} status and {value} name")
    public void thenIShouldBeNotifiedWithBrandCreated(Boolean status, String name) {
        Map<String, Object> brand = brandContext.getCreated(identifierContext.get());

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                                hasEntry("activated", (Object) status)));
    }

    @Then("I should be notified with the brand changed event containing {status} status and {value} name")
    public void thenIShouldBeNotifiedWithBrandChanged(Boolean status, String name) {
        Map<String, Object> brand = brandContext.getChanged(identifierContext.get());

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                                hasEntry("activated", (Object) status)));
    }

    @Then("I should be notified with the brand deleted event")
    public void thenIShouldBeNotifiedWithBrandDeleted() {
        Map<String, Object> brand = brandContext.getDeleted(identifierContext.get());

        assertThat(brand, notNullValue());
    }

}