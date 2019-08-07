package ru.otus.zaikin.framework.common;

import com.codeborne.selenide.Selenide;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Pages for testing
 */
@Log4j2
public final class Pages {
    private Map<String, BasePage> pages;
    private BasePage currentPage;

    public Pages() {
        pages = Maps.newHashMap();
    }

    public BasePage getCurrentPage() {
        if (currentPage == null) throw new IllegalStateException("Current page not found");
        return currentPage.initialize();
    }

    public void setCurrentPage(BasePage page) {
        this.currentPage = page;
    }


    public static <T extends BasePage> void withPage(Class<T> clazz, boolean checkIfElementsAppeared, Consumer<T> consumer) {
        T page = getPage(clazz, checkIfElementsAppeared);
        consumer.accept(page);
    }

    public BasePage get(String pageName) {
        return Selenide.page(getPageFromPagesByName(pageName)).initialize();
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePage> T get(Class<T> clazz, String name) {
        BasePage page = Selenide.page(getPageFromPagesByName(name)).initialize();

        if (!clazz.isInstance(page)) {
            throw new IllegalStateException(name + " page is not a instance of " + clazz + ". Named page is a " + page);
        }
        return (T) page;
    }

    private Map<String, BasePage> getPageMapInstanceInternal() {
        return pages;
    }

    private BasePage getPageFromPagesByName(String pageName) throws IllegalArgumentException {
        BasePage page = getPageMapInstanceInternal().get(pageName);
        if (page == null)
            throw new IllegalArgumentException(pageName + " page is not declared in a list of available pages");
        return page;
    }


    public <T extends BasePage> void put(String pageName, T page) throws IllegalArgumentException {
        if (page == null)
            throw new IllegalArgumentException("Page is null");
        pages.put(pageName, page);
    }

    public static <T extends BasePage> T getPage(Class<T> clazz, boolean checkIfElementsAppeared) {
        T page = Selenide.page(clazz);
        if (checkIfElementsAppeared) {
            page.initialize().isAppeared();
        }
        return page;
    }

    @SneakyThrows
    public void put(String pageName, Class<? extends BasePage> page) {
        if (page == null)
            throw new IllegalArgumentException("Page is null");
        Constructor<? extends BasePage> constructor = page.getDeclaredConstructor();
        constructor.setAccessible(true);
        BasePage p = page.newInstance();
        pages.put(pageName, p);
    }
}
