package br.com.totvs.raas.product.commad.test.steps.message;

import br.com.totvs.raas.core.test.steps.ResultContext;
import static br.com.totvs.raas.product.commad.test.ProductQueryTestConstants.*;
import br.com.totvs.raas.product.commad.test.steps.AbstractStep;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageSteps extends AbstractStep {

    @Autowired
    private ResultContext resultContext;

    @Autowired
    private MessageContext messageContext;

    @Then("I should see {int} messages")
    public void thenIShouldSee(int quantity) {
        List<String> messages = resultContext.errorMessages();

        assertEquals(quantity, messages.size());

        messageContext.setMessages(messages);
    }

    @Then("I should see {value} message")
    public void thenIShouldSee(String message) {
        assertEquals(message, messageContext.next());
    }

    @Then("I should only see {value} message")
    public void thenIShouldOnlySee(String message) {
        List<String> messages = resultContext.errorMessages();

        assertEquals(JUST_A, messages.size());
        assertEquals(message, messages.get(FIRST_ITEM));
    }

}