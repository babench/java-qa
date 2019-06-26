package ru.otus.zaikin.testlink.integration;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class TestLinkApiHolder {
    private TestLinkAPI client;

    public TestLinkApiHolder(String url, String devKey) {

        try {
            URL testlinkURL = null;
            testlinkURL = new URL(url);
            client = new TestLinkAPI(testlinkURL, devKey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public TestLinkAPI getClient() {
        return client;
    }
}