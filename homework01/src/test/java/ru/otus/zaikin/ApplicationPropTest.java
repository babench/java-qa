package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.zaikin.logger.TestStatus;

import java.util.Properties;

@Log4j2
public class ApplicationPropTest {
    Properties properties;

    @Before
    public void setUp() {
        properties = AppProperties.getInstance().getProperties();
    }

    @Test
    public void shouldBeSeverity() {
        String property = properties.getProperty("tests.status.severity");
        TestStatus testStatus = TestStatus.valueOf(property);
        Assert.assertNotNull(testStatus);
    }
}