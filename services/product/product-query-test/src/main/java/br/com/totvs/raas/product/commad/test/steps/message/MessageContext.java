package br.com.totvs.raas.product.commad.test.steps.message;

import br.com.totvs.raas.core.test.context.Cleaned;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

import static java.util.Objects.nonNull;

@Component
public class MessageContext implements Cleaned {

    private Iterator<String> messages;

    public void setMessages(Collection<String> messages) {
        if (nonNull(messages)) {
            this.messages = messages.iterator();
        }
    }

    public String next() {
        if (messages == null) {
            throw new RuntimeException("There is no message");
        }
        if (messages.hasNext()) {
            return messages.next();
        }
        throw new RuntimeException("There is no more message");
    }

    @Override
    public void clear() {
        messages = null;
    }

}
