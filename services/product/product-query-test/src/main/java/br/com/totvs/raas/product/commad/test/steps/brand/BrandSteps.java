package br.com.totvs.raas.product.commad.test.steps.brand;

import br.com.totvs.raas.core.test.context.IteratorContext;
import br.com.totvs.raas.core.test.context.Result;
import br.com.totvs.raas.core.test.steps.IdentifierContext;
import br.com.totvs.raas.core.test.steps.ResultContext;
import br.com.totvs.raas.product.commad.test.adapter.messaging.BrandEventProducer;
import br.com.totvs.raas.product.commad.test.adapter.persistence.BrandRepository;
import static br.com.totvs.raas.product.commad.test.ProductQueryTestConstants.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static br.com.totvs.hamcrest.ext.Assert.assertThat;
import static br.com.totvs.hamcrest.ext.matcher.Matchers.allOf;
import static br.com.totvs.hamcrest.ext.matcher.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;

public class BrandSteps {

    @Autowired
    private IdentifierContext identifierContext;

    @Autowired
    private BrandAdapter brandAdapter;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ResultContext resultContext;

    @Autowired
    private IteratorContext iteratorContext;

    @Autowired
    private BrandEventProducer brandEventProducer;

    @Given("I have a {status} Brand with {value} name")
    public void givenIHaveABrandWith(Boolean status, String name) {
        String id = brandRepository.save(name, status);

        identifierContext.set(id);
    }

    @When("I notify {status} Brand Changed with {value} name")
    public void givenIHaveABrandChangedWith(Boolean status, String name) {
        String id = identifierContext.get();
        brandEventProducer.change(id, name, status);
    }

    @When("I notify {status} Brand Created with {value} name")
    public void givenIHaveABrandCreatedWith(Boolean status, String name) {
        String id = brandEventProducer.create(name, status);

        identifierContext.set(id);
    }

    @When("I search the brand")
    public void whenIFindTheBrand() {
        Result result = brandAdapter.findBy(identifierContext.get());

        resultContext.set(result);
    }

    @When("I search for all brand")
    public void whenIFindForAllBrand() {
        Result result = brandAdapter.findAll();

        resultContext.set(result);
    }

    @Then("I should see the brand with {status} status and {value} name")
    public void thenIShouldSeeTheBrandWith(Boolean status, String name) {
        Map<String, Object> brand = resultContext.getSuccess().toMap();

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                                hasEntry("activated", (Object) status)));
    }

    @Then("I should only see brand with {status} status and {value} name")
    public void thenIShouldOnlySeeBrand(Boolean status, String name) {
        List<Map<String, Object>> brands = resultContext.getSuccess().toList();

        assertEquals(JUST_A, brands.size());
        assertThat(brands.get(FIRST_ITEM),
                allOf(hasEntry("name", (Object) name),
                      hasEntry("activated", (Object) status)));
    }

    @Then("I should see '{int}' brands")
    public void thenIShouldSeeBrands(int quantity) {
        List<Map<String, Object>> brands = resultContext.getSuccess().toList();

        assertEquals(quantity, brands.size());

        iteratorContext.setItems(brands);
    }

    @Then("I should see a brand with {status} status and {value} name")
    public void thenIShouldSeeABrand(Boolean status, String name) {
        Map<String, Object> brand = (Map<String, Object>) iteratorContext.next();

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                hasEntry("activated", (Object) status)));
    }

    @Then("I should have a brand with {status} status and {value} name")
    public void thenIShouldHaveABrand(Boolean status, String name) {
        Map<String, Object> brand = brandRepository.findBy(identifierContext.get());

        assertThat(brand, allOf(hasEntry("name", (Object) name),
                hasEntry("activated", (Object) status)));
    }

}