package br.com.totvs.raas.product.commad.test.steps;

import br.com.totvs.raas.core.test.context.Cleaned;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SequenceContext implements Cleaned {

    private long sequence = 0L;

    public long next() {
        return sequence++;
    }

    @Override
    public void clear() {
        this.sequence = 0L;
    }
}
