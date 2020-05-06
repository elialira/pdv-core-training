package br.com.totvs.raas.core.validator;

import br.com.totvs.raas.core.exception.ViolationException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
@RequiredArgsConstructor
public class Validator {

    private final Collection<Validation> validations;

    public void validate(Function<Collection<Violation>, ? extends RuntimeException> exception) {
        Collection<Violation> violations = getViolations();

        if (!violations.isEmpty()) {
            throw exception.apply(violations);
        }
    }

    public void validate() {
        validate(ViolationException::new);
    }

    private List<Violation> getViolations() {
        return validations.stream()
                .filter(Validation::isNotValid)
                .map(Validation::toViolation)
                .collect(Collectors.toList());
    }

}
