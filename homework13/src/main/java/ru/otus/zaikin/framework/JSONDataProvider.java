package ru.otus.zaikin.framework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * TestNG JSON DataProvider Utility Class
 */
public class JSONDataProvider {
    public static String dataFile = "";
    public static String testCaseName = "NA";

    public JSONDataProvider() {
    }

    /**
     * fetchData method to retrieve test data for specified method
     *
     * @param method test method name
     * @return Object[][]
     */
    @DataProvider(name = "json")
    public static Object[][] fetchData(Method method) throws Exception {
        Object rowID, description;
        Object[][] result;
        testCaseName = method.getName();
        List<JSONObject> testDataList = new ArrayList<>();
        JSONArray testData = (JSONArray) extractDataJSON(dataFile).get(method.getName());
        for (Object testDatum : testData) testDataList.add((JSONObject) testDatum);

        // include Filter
        if (System.getProperty("includePattern") != null) {
            String include = System.getProperty("includePattern");
            List<JSONObject> newList = new ArrayList<>();
            String[] tests = include.split(",", -1);

            for (String getTest : tests) {
                for (JSONObject jsonObject : testDataList) {
                    if (jsonObject.toString().contains(getTest))
                        newList.add(jsonObject);
                }
            }
            // reassign testRows after filtering tests
            testDataList = newList;
        }

        // exclude Filter
        if (System.getProperty("excludePattern") != null) {
            String exclude = System.getProperty("excludePattern");
            String[] tests = exclude.split(",", -1);

            // start at end of list and work backwards so index stays in sync
            for (String getTest : tests)
                for (int i = testDataList.size() - 1; i >= 0; i--)
                    if (testDataList.get(i).toString().contains(getTest)) testDataList.remove(testDataList.get(i));
        }

        // create object for data provider to return
        try {
            result = new Object[testDataList.size()][testDataList.get(0).size()];
            for (int i = 0; i < testDataList.size(); i++) {
                rowID = testDataList.get(i).get("rowID");
                description = testDataList.get(i).get("description");
                result[i] = new Object[]{rowID, description, testDataList.get(i)};
            }
        } catch (IndexOutOfBoundsException ie) {
            result = new Object[0][0];
        }

        return result;
    }

    /**
     * Get JSON data from file
     *
     * @param file with test data in resources folder
     * @return JSONObject
     */
    public static JSONObject extractDataJSON(String file) throws Exception {
        URL resource = JSONDataProvider.class.getClassLoader().getResource(file);
        if (resource == null) throw new IllegalArgumentException();
        FileReader reader = new FileReader(resource.getFile());
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(reader);
    }
}