package br.com.totvs.raas.core.test.steps;

import br.com.totvs.raas.core.test.context.Fault;
import br.com.totvs.raas.core.test.context.Result;
import br.com.totvs.raas.core.test.context.SimpleContext;
import br.com.totvs.raas.core.test.context.Success;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ResultContext extends SimpleContext<Result> {

    public Success getSuccess() {
        return cast(orElseThrow(), Success.class);
    }

    public Fault getFault() {
        return cast(orElseThrow(), Fault.class);
    }

    public List<String> errorMessages() {
        return getFault().getMessages();
    }

    private <T> T cast(Result value, Class<T> tClass) {
        if (!tClass.isInstance(value)) {
            throw new RuntimeException("Result is not successful. The Result is " + value);
        }
        return (T) value;
    }

    private Result orElseThrow() {
        return Optional.ofNullable(get())
                .orElseThrow(() -> new RuntimeException("Rusult cannot be null"));
    }

}
