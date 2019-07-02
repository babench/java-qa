package ru.otus.zaikin.framework;

import lombok.extern.log4j.Log4j2;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;
import static ru.otus.zaikin.framework.DriverType.FIREFOX;

@Log4j2
public class DriverFactory {

    private RemoteWebDriver webDriver;
    private DriverType selectedDriverType;

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private BrowserMobProxy browserMobProxy;
    private boolean usingBrowserMobProxy = false;

    public DriverFactory() {
        DriverType driverType = FIREFOX;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public BrowserMobProxy getBrowserMobProxy() {
        if (usingBrowserMobProxy) {
            return browserMobProxy;
        }
        return null;
    }

    public RemoteWebDriver getDriver(boolean useBrowserMobProxy) throws MalformedURLException {
        if (useBrowserMobProxy != usingBrowserMobProxy) {
            quitDriver();
        }
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType, useBrowserMobProxy);
        }

        return webDriver;
    }

    public RemoteWebDriver getDriver() throws MalformedURLException {
        return getDriver(usingBrowserMobProxy);
    }

    public void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
            usingBrowserMobProxy = false;
        }
    }

    private void instantiateWebDriver(DriverType driverType, boolean useBrowserMobProxy) throws MalformedURLException {
        log.debug("Local Operating System: " + operatingSystem);
        log.debug("Local Architecture: " + systemArchitecture);
        log.debug("Selected Browser: " + selectedDriverType);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        log.debug("proxyEnabled:" + proxyEnabled);
        log.debug("useBrowserMobProxy" + useBrowserMobProxy);
        if (proxyEnabled || useBrowserMobProxy) {
            Proxy proxy;
            if (useBrowserMobProxy) {
                usingBrowserMobProxy = true;
                browserMobProxy = new BrowserMobProxyServer();
                browserMobProxy.start();
                if (proxyEnabled) {
                    browserMobProxy.setChainedProxy(new InetSocketAddress(proxyHostname, proxyPort));
                }
                proxy = ClientUtil.createSeleniumProxy(browserMobProxy);
            } else {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }
            desiredCapabilities.setCapability(PROXY, proxy);
        }

        webDriver = driverType.getWebDriverObject(desiredCapabilities);
    }
}