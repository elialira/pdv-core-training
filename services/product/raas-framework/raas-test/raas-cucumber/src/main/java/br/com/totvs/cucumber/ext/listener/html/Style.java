package br.com.totvs.cucumber.ext.listener.html;

import java.util.stream.Stream;

public enum Style {

    PARAM("color: #2196f3;"),
    STEP("font-weight: bold;"),
    INFO("color: #0b7dda;"),
    PASSED("color: #00af00;"),
    FAILED("color: #b71c1c;"),
    SKIPPED("color: #009687;"),
    UNDEFINED("color: #009687;");

    private String value;

    Style(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String chave) {
        return Stream.of(Style.values())
                .filter(style -> style.name().equalsIgnoreCase(chave))
                .findFirst()
                .map(Style::getValue)
                .orElse("");
    }

}
