package br.com.totvs.raas.core.i18;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class TranslatedMessage implements Serializable {

    @JsonUnwrapped
    private final Object target;

    private final String message;

}
