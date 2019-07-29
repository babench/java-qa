package ru.otus.zaikin.web;

import org.json.simple.JSONObject;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.JSONDataProvider;
import ru.otus.zaikin.service.CalculatorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = {CalculatorController.class, CalculatorService.class})
public class CalculatorControllerTest extends AbstractTestNGSpringContextTests {
    private static final String DATA_FILE_IN_RESOURCES = "./testdata/CalculatorServiceTest/TestData.json";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeClass(alwaysRun = true, enabled = true)
    protected void testClassSetup(ITestContext context) {
        JSONDataProvider.dataFile = DATA_FILE_IN_RESOURCES;
    }

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc001_plus(String rowID,
                           String description,
                           JSONObject testData) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/calc")
                .param("operation", "PLUS")
                .param("a", testData.get("a").toString())
                .param("b", testData.get("b").toString())
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc002_multiply(String rowID,
                               String description,
                               JSONObject testData) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/calc")
                .param("operation", "MULTIPLY")
                .param("a", testData.get("a").toString())
                .param("b", testData.get("b").toString())
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc003_minus(String rowID,
                            String description,
                            JSONObject testData) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/calc")
                .param("operation", "MINUS")
                .param("a", testData.get("a").toString())
                .param("b", testData.get("b").toString())
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class)
    public void tc004_divide(String rowID,
                             String description,
                             JSONObject testData) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/calc")
                .param("operation", "DIVIDE")
                .param("a", testData.get("a").toString())
                .param("b", testData.get("b").toString())
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(testData.get("result").toString()));
    }

    @Test(dataProvider = "json", dataProviderClass = JSONDataProvider.class, expectedExceptions = NestedServletException.class)
    public void tc005_divide_zero(String rowID,
                                  String description,
                                  JSONObject testData) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mockMvc.perform(get("/calc")
                .param("operation", "DIVIDE")
                .param("a", testData.get("a").toString())
                .param("b", testData.get("b").toString())
                .accept(MediaType.ALL));
    }
}