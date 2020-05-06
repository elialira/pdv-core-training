package br.com.totvs.raas.core.validator;

import br.com.totvs.raas.core.i18.Translation;
import br.com.totvs.raas.core.i18.Translator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class Violation implements Serializable, Translation {

    private final String code;
    private final Object value;

    public String toMessage(Translator translator) {
        return translator.translate(code, value);
    }

}
