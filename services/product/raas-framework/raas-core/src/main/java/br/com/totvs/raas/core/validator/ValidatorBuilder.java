package br.com.totvs.raas.core.validator;

import java.util.ArrayList;
import java.util.Collection;

public class ValidatorBuilder {

    interface Constants {
        String HAS_LENGTH = "validation.hasLength";
        String NOT_NULL = "validation.notNull";
    }

    private Collection<Validation> validations;

    private ValidatorBuilder() {
        this.validations = new ArrayList<>();
    }

    public static ValidatorBuilder builder() {
        return new ValidatorBuilder();
    }

    public ValidatorBuilder notNull(String code, Object value) {
        validations.add(new Validation.NotNull(value, code));
        return this;
    }

    public ValidatorBuilder hasLength(String code, String value) {
        validations.add(new Validation.HasLength(value, code));
        return this;
    }


    public Validator build() {
        return new Validator(validations);
    }

}
