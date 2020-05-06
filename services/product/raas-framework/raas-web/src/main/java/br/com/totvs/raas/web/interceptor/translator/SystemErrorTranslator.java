package br.com.totvs.raas.web.interceptor.translator;

public class SystemErrorTranslator implements ErrorTranslator {

    @Override
    public String toMessage() {
        return "Internal Server Error";
    }

    @Override
    public String toCode() {
        return "exception.system";
    }

}
