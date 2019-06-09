package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.fail;

@Log4j2
public class TestInfluxDB {

    @Rule
    public CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Test
    public void shouldFailWithRandowDelay() throws InterruptedException {
        log.error("fail");
        Thread.sleep(new Random().nextInt(1000));
        fail();
    }

    @Test
    public void shouldSuccessWithRandowDelay() throws InterruptedException {
        log.debug("shouldSuccess");
        Thread.sleep(new Random().nextInt(1000));
    }

    @Test
    public void shouldSuccessWithRandowDelay1() throws InterruptedException {
        log.debug("shouldSuccess1");
        Thread.sleep(new Random().nextInt(5000));
    }

    @Test
    public void shouldSuccessWithRandowDelay2() throws InterruptedException {
        log.debug("shouldSuccess2");
        Thread.sleep(new Random().nextInt(10000));
    }
}
