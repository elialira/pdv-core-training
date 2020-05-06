package br.com.totvs.raas.core.validator;

import br.com.totvs.raas.core.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidatorBuilderTest extends AbstractTest {

    @Test
    void shouldCreateValidatorWithHasLength() {
        Validator actual = ValidatorBuilder
                .builder()
                .hasLength("Value should not be empty or null.", "")
                .build();

        String expected = "Validator(validations=[Validation(value=Optional[], code=Value should not be empty or null.)])";

        assertEquals(expected, toString(actual));
    }

    @Test
    void shouldCreateValidatorWithNotNull() {
        Validator actual = ValidatorBuilder
                .builder()
                .notNull("Value should not be null.", (Object) "")
                .build();

        String expected = "Validator(validations=[Validation(value=Optional[], code=Value should not be null.)])";

        assertEquals(expected, toString(actual));
    }

    private String toString(Object value) {
        if (value != null) {
            return value.toString();
        }
        return null;
    }

}