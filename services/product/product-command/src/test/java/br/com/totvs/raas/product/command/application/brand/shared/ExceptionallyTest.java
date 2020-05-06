package br.com.totvs.raas.product.command.application.brand.shared;

import br.com.totvs.raas.core.exception.BusinessException;
import br.com.totvs.raas.product.command.AbstractTest;
import br.com.totvs.raas.product.command.ProductCommandConstants.BrandConstants;
import br.com.totvs.raas.product.command.domain.model.brand.BrandNotFoundException;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExceptionallyTest extends AbstractTest {

    @Test
    public void shouldThrowException() {
        try {
            Exceptionally.builder()
                    .build()
                    .apply(new Exception("Exception"));
            fail();
        } catch (Exception cause) {
            String expected = "java.lang.Exception: Exception";

            assertEquals(expected, cause.getMessage());
        }
    }

    @Test
    public void givenHasNotFoundException_shouldThrowException() {
        try {
            Exceptionally.builder()
                    .notFound(BrandNotFoundException::new)
                    .build()
                    .apply(new Exception("Exception"));
            fail();
        } catch (Exception cause) {
            String expected = "java.lang.Exception: Exception";

            assertEquals(expected, cause.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeException() {
        try {
            Exceptionally.builder()
                    .notFound(BrandNotFoundException::new)
                    .build()
                    .apply(new RuntimeException("Runtime Exception"));
            fail();
        } catch (RuntimeException cause) {
            String expected = "Runtime Exception";

            assertEquals(expected, cause.getMessage());
        }
    }

    @Test
    public void givenHasNotFoundException_shouldThrowBusinessException() {
        try {
            Exceptionally.builder()
                    .notFound(BrandNotFoundException::new)
                    .build()
                    .apply(new AggregateNotFoundException(BrandConstants.ID, "Brand not found"));
            fail();
        } catch (BusinessException cause) {
            String expected = "BusinessException(code=exception.brand_not_found, arguments=[])";

            assertEquals(expected, toString(cause));
        }

    }

    protected String toString(BusinessException cause) {
        return Optional.ofNullable(cause)
                .map(Object::toString)
                .orElse(null);
    }


}