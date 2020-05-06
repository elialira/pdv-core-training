package br.com.totvs.raas.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError implements Serializable {

    private String code;
    private String message;
    private String detailedMessage;
    private String trackingCode;
    private Object errors;

    public Map<String, Object> toMap() {
        Map<String, Object> error = new LinkedHashMap<>();
        if (nonNull(code)) { error.put("code", code); }
        if (nonNull(message)) { error.put("message", message); }
        if (nonNull(detailedMessage)) { error.put("detailedMessage", detailedMessage); }
        if (nonNull(trackingCode)) { error.put("trackingCode", trackingCode); }
        if (nonNull(errors)) { error.put("errors", errors); }
        return error;
    }

}
