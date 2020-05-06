package br.com.totvs.raas.core.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@ToString
@Getter
@RequiredArgsConstructor
public abstract class Validation implements Validated {

    private final Optional<Object> value;
    private final String code;

    public Validation(Object value, String code) {
        this(Optional.ofNullable(value), code);
    }

    public Violation toViolation() {
        return new Violation(code, value.orElse(null));
    }

    @Override
    public boolean isNotValid() {
        return !isValid();
    }

    public static class NotNull extends Validation {

        public NotNull(Object value, String code) {
            super(value, code);
        }

        @Override
        public boolean isValid() {
            return getValue().isPresent();
        }
    }

    public static class Null extends Validation {

        public Null(Object value, String code) {
            super(value, code);
        }

        @Override
        public boolean isValid() {
            return !getValue().isPresent();
        }
    }

    public static class HasLength extends Validation {

        public HasLength(Object value, String code) {
            super(value, code);
        }

        @Override
        public boolean isValid() {
            return getValue()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(value -> !value.isEmpty())
                    .orElse(false);
        }

    }

}
