package br.com.totvs.cucumber.ext.listener.html;

import com.aventstack.extentreports.markuputils.Markup;
import io.cucumber.plugin.event.Argument;
import io.cucumber.plugin.event.Step;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class StepHtml implements Markup {

    private static final String BR = "</br>";
    private static final String EMPTY = "";
    private Step step;
    private List<Argument> arguments;
    private List<List<String>> cells;
    private Result result;

    public StepHtml(Step step, List<Argument> arguments, Result result, List<List<String>> cells) {
        this.step = step;
        this.arguments = arguments;
        this.result = result;
        this.cells = cells;
    }

    private String toTable() {
        if (cells != null) {
            return BR + new TableHtml(result.getStatus().name(), cells).getMarkup();
        }
        return EMPTY;
    }

    private String toError() {
        if (Status.FAILED.equals(result.getStatus())) {
            return BR + printStackTrace(result.getError());
        }
        return EMPTY;
    }

    private static String printStackTrace(Throwable error) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        error.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    @Override
    public String getMarkup() {
        return "<span style=\"" + Style.STEP.getValue()
                                 +  Style.getValue(result.getStatus().name())
                                 + "\">" + step.getKeyWord() + "</span>" + getText()
                + toTable() + toError();
    }

    private String getText() {
        String text = step.getText();
        int count = 0;
        if (arguments != null) {
            for (Argument argument : arguments) {
                String spanStart = "<span style=\"" + Style.STEP.getValue() + Style.PARAM.getValue() + "\">";
                String spanEnd = "</span>";
                text = replace(text, spanStart,argument.getStart() + count);
                count += spanStart.length();
                text = replace(text, spanEnd,argument.getEnd() + count);
                count += spanEnd.length();
            }
        }
        return text;
    }

    public String replace(String text, String value, int index) {
        return text.substring(0, index) + value + text.substring(index);
    }

}
