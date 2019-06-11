package ru.otus.zaikin.logger;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;

@Log4j2
public class TestStatusTest {

    @Test
    public void shouldTransformProperly() {
        log.debug("TestStatusTest.shouldTransformProperly");
        TestStatus testStatus = TestStatus.PASS;
        Assert.assertEquals(TestStatus.PASS, testStatus);
        Assert.assertEquals("PASS", testStatus.toString());
        Assert.assertEquals("PASS", String.valueOf(testStatus));
        Assert.assertEquals(1, testStatus.ordinal());
    }
}