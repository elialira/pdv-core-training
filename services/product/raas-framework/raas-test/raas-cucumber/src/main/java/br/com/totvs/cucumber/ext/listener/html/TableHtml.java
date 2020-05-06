package br.com.totvs.cucumber.ext.listener.html;

import com.aventstack.extentreports.markuputils.Markup;

import java.util.List;

public class TableHtml implements Markup {

    private final String status;
    private final List<List<String>> data;

    public TableHtml(String status, List<List<String>> data) {
        this.status = status;
        this.data = data;
    }

    @Override
    public String getMarkup() {
        StringBuilder html = new StringBuilder();
        html.append("<table>");
        data.stream().forEach(
                row -> {
                    html.append("<tr>");
                    row.stream().forEach(
                            cell -> {
                                html.append("<td style=\"" + Style.getValue(status) + "\">");
                                html.append(cell);
                                html.append("</td>");
                            }
                    );
                    html.append("</tr>");
                });
        html.append("</table>");
        return html.toString();
    }

}
