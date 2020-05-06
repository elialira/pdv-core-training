package br.com.totvs.raas.product.commad.test.steps;

import br.com.totvs.raas.core.test.context.Cleaned;
import br.com.totvs.raas.product.commad.test.port.adapter.persistence.event.EventStore;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class Scenario {

    @Autowired
    private EventStore eventStoreRepository;

    @Autowired
    private List<Cleaned> cleaneds;

    @ParameterType(value = "'(.*?)'")
    public String value(String value) {
        if ("NULL".equalsIgnoreCase(value)) {
            return null;
        }
        return value;
    }

    @ParameterType(value = "'(null|activated|disabled|active|inactive)'")
    public Boolean status(String status) {
        if ("NULL".equalsIgnoreCase(status)) {
            return null;
        }
        return Arrays.asList("active", "activated")
                .contains(status);
    }

    @Before
    public void setUp() {
        eventStoreRepository.deleteAll();
        cleaneds.clear();
    }

    @After
    public void teawDown() {
        eventStoreRepository.deleteAll();
        cleaneds.clear();
    }

}
