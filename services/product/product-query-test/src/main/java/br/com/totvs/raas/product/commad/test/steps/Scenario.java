package br.com.totvs.raas.product.commad.test.steps;

import br.com.totvs.raas.core.test.context.Cleaned;
import br.com.totvs.raas.product.commad.test.adapter.persistence.BrandRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class Scenario {

    @Autowired
    private BrandRepository brandRepository;

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
        clear();
    }

    @After
    public void teawDown() {
        clear();
    }

    private void clear() {
        brandRepository.deleteAll();
        cleaneds.clear();
    }

}
