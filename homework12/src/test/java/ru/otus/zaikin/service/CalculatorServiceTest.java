package ru.otus.zaikin.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.JSONDataProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = CalculatorService.class)
public class CalculatorServiceTest extends AbstractTestNGSpringContextTests {
    private static final String DATA_FILE_IN_RESOURCES = "./testdata/CalculatorServiceTest/TestData.json";

    @Autowired
    private CalculatorService calculatorService;

    @BeforeClass(alwaysRun = true, enabled = true)
    protected void testClassSetup(ITestContext context) {
        JSONDataProvider.dataFile = DATA_FILE_IN_RESOURCES;
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc001_plus(String rowID,
                           String description,
                           JSONObject testData) {
        assertThat(calculatorService.plus(Integer.valueOf(testData.get("a").toString()), Integer.valueOf(testData.get("b").toString())))
                .isEqualTo(Integer.valueOf(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc002_multiply(String rowID,
                               String description,
                               JSONObject testData) {
        assertThat(calculatorService.multiply(Integer.valueOf(testData.get("a").toString()), Integer.valueOf(testData.get("b").toString())))
                .isEqualTo(Integer.valueOf(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc003_minus(String rowID,
                            String description,
                            JSONObject testData) {
        assertThat(calculatorService.minus(Integer.valueOf(testData.get("a").toString()), Integer.valueOf(testData.get("b").toString())))
                .isEqualTo(Integer.valueOf(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc004_divide(String rowID,
                             String description,
                             JSONObject testData) {
        assertThat(calculatorService.divide(Integer.valueOf(testData.get("a").toString()), Integer.valueOf(testData.get("b").toString())))
                .isEqualTo(Integer.valueOf(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class, expectedExceptions = ArithmeticException.class)
    public void tc005_divide_zero(String rowID,
                                  String description,
                                  JSONObject testData) {
        calculatorService.divide(Integer.valueOf(testData.get("a").toString()), Integer.valueOf(testData.get("b").toString()));
    }
}