package br.com.totvs.cucumber.ext.listener;

import br.com.totvs.cucumber.ext.listener.html.StepHtml;
import br.com.totvs.cucumber.ext.listener.html.Style;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.core.internal.gherkin.ast.Step;
import io.cucumber.core.internal.gherkin.ast.*;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.Plugin;
import io.cucumber.plugin.event.*;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ExtentCucumberFormatter implements Plugin, EventListener {

    private static ExtentReports extentReports;
    private static ExtentHtmlReporter htmlReporter;
    private ExtentTest featureTest;
    private ExtentTest scenarioTest;

    private ExtentTest scenarioOutlineTest;
    private ScenarioOutline currentScenarioOutline;

    private TestSourcesModel model = new TestSourcesModel();
    private URI currentFeatureFile;
    private Examples currentExamples;

    public ExtentCucumberFormatter() { }

    public ExtentCucumberFormatter(File file) {
        createExtentHtmlReport(file);
        createExtentReport();
     }

    private static void createExtentHtmlReport(File file) {
        if (htmlReporter != null) {
            return;
        }
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        htmlReporter = new ExtentHtmlReporter(file);
    }


    private static void createExtentReport() {
        if (extentReports != null) {
            return;
        }
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @Override
    public void setEventPublisher(final EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::handleTestSourceRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);

        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);

        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }

    private void handleTestSourceRead(TestSourceRead event) {
        model.addTestSourceReadEvent(event.getUri(), event);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        handleStartOfFeature(event.getTestCase());
        handleScenarioOutline(event.getTestCase());
        handleScenario(event.getTestCase());
        if (model.hasBackground(currentFeatureFile, event.getTestCase().getLine())) {
//            jsFunctionCall("background", createBackground(event.getTestCase()));
        }
    }

    private void handleStartOfFeature(TestCase testCase) {
        if (currentFeatureFile == null || !currentFeatureFile.equals(testCase.getUri())) {
            currentFeatureFile = testCase.getUri();
            startOfFeature(model.getFeature(testCase.getUri()));
        }
    }

    public void startOfFeature(Feature feature) {
        featureTest = extentReports.createTest(feature.getName());

        feature.getTags().stream()
                .map(Tag::getName)
                .forEach(featureTest::assignCategory);
    }

    private void handleScenarioOutline(TestCase testCase) {
        TestSourcesModel.AstNode astNode = model.getAstNode(currentFeatureFile, testCase.getLine());
        if (TestSourcesModel.isScenarioOutlineScenario(astNode)) {
            ScenarioOutline scenarioOutline = (ScenarioOutline) TestSourcesModel.getScenarioDefinition(astNode);
            if (currentScenarioOutline == null || !currentScenarioOutline.equals(scenarioOutline)) {
                currentScenarioOutline = scenarioOutline;
                scenarioOutline(scenarioOutline);
            }
            Examples examples = (Examples) astNode.parent.node;
            if (currentExamples == null || !currentExamples.equals(examples)) {
                currentExamples = examples;
                examples(currentExamples);
            }
        } else {
            scenarioOutlineTest = null;
            currentScenarioOutline = null;
            currentExamples = null;
        }
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        scenarioOutlineTest = featureTest
                .createNode(scenarioOutline.getKeyword() + ": " + scenarioOutline.getName());

        scenarioOutline.getSteps()
                .stream()
                .map(this::toInfoStep)
                .forEach(scenarioOutlineTest::info);
    }

    private String toInfoStep(Step step) {
        String value = step.getText().replace("<", "&lt;")
                                     .replace(">", "&gt;");

        return "<span style=\"" + Style.STEP.getValue() + Style.PARAM.getValue() + "\">"
                + step.getKeyword() + "</span>" + value;
    }

    public void examples(Examples examples) {
        String[] header = examples.getTableHeader()
                .getCells()
                .stream()
                .map(TableCell::getValue)
                .collect(toList())
                .toArray(String[]::new);

        List<String[]> data = examples.getTableBody()
                .stream()
                .map(TableRow::getCells)
                .map(cells -> cells.stream()
                                   .map(TableCell::getValue)
                                   .collect(toList())
                                   .toArray(String[]::new))
                .collect(toList());
        data.add(0, header);

        scenarioOutlineTest.info(MarkupHelper.createTable(data.toArray(String[][]::new)));
    }

    private void handleScenario(TestCase testCase) {
        ExtentTest currentTest =  featureTest;
        if (scenarioOutlineTest != null) {
            currentTest = scenarioOutlineTest;
        }

        scenarioTest = currentTest.createNode("Scenario: " + testCase.getName());

        testCase.getTags()
                .stream()
                .forEach(scenarioTest::assignCategory);
    }


    private void handleTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
//            if (isFirstStepAfterBackground(testStep)) {
//                jsFunctionCall("scenario", currentTestCaseMap);
//                currentTestCaseMap = null;
//            }
//            createTestStep(testStep);
//            jsFunctionCall("match", createMatchMap((PickleStepTestStep) event.getTestStep()));
        }
    }


    private void handleTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            createResultMap(((PickleStepTestStep) event.getTestStep()).getStep(),
                            ((PickleStepTestStep) event.getTestStep()).getDefinitionArgument(),
                    event.getResult());
        } else if (event.getTestStep() instanceof HookTestStep) {
            HookTestStep hookTestStep = (HookTestStep) event.getTestStep();
//            jsFunctionCall(getFunctionName(hookTestStep), createResultMap(event.getResult()));
        } else {
            throw new IllegalStateException();
        }
    }

    private void createResultMap(io.cucumber.plugin.event.Step step, List<Argument> arguments, Result result) {
        List<List<String>> cells = Optional.ofNullable(step)
                .map(io.cucumber.plugin.event.Step::getArgument)
                .filter(DataTableArgument.class::isInstance)
                .map(DataTableArgument.class::cast)
                .map(DataTableArgument::cells)
                .orElse(null);

        StepHtml stepHtml = new StepHtml(step, arguments, result, cells);

        if (Status.PASSED.equals(result.getStatus())) {
            scenarioTest.pass(stepHtml);
        } else if (Status.FAILED.equals(result.getStatus())) {
            scenarioTest.fail(stepHtml);
        } else if (Status.SKIPPED.equals(result)) {
            scenarioTest.skip(stepHtml);
        } else if (Status.UNDEFINED.equals(result)) {
            scenarioTest.skip(stepHtml);
        }
    }

    private void handleTestRunFinished(TestRunFinished event) {
        extentReports.flush();
    }

    static ExtentHtmlReporter getExtentHtmlReport() {
        return htmlReporter;
    }

    static ExtentReports getExtentReport() {
        return extentReports;
    }

}


