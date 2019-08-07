package ru.otus.zaikin.framework.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.annotations.Optional;
import ru.otus.zaikin.framework.utils.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Log4j2
public abstract class BasePage extends ElementsContainer {
    private static final String WAITING_APPEAR_TIMEOUT_IN_MILLISECONDS = "10000";

    public BasePage() {
        super();
    }

    public BasePage getBlock(String blockName) {
        return (BasePage) java.util.Optional.ofNullable(namedElements.get(blockName))
                .orElseThrow(() -> new IllegalArgumentException("Block " + blockName + " not exists on " + this.getClass().getName()));
    }

    public List<SelenideElement> getBlockElements(String blockName) {
        return getBlock(blockName).namedElements.entrySet().stream()
                .map(x -> ((SelenideElement) x.getValue())).collect(toList());
    }

    public SelenideElement getBlockElement(String blockName, String elementName) {
        return ((SelenideElement) getBlock(blockName).namedElements.get(elementName));
    }

    public SelenideElement getElement(String elementName) {
        return (SelenideElement) java.util.Optional.ofNullable(namedElements.get(elementName))
                .orElseThrow(() -> new IllegalArgumentException("Element " + elementName + " not exists on " + this.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    public List<SelenideElement> getElementsList(String listName) {
        Object value = namedElements.get(listName);
        if (!(value instanceof List)) {
            throw new IllegalArgumentException("List " + listName + " not exists on " + this.getClass().getName());
        }
        Stream<Object> s = ((List) value).stream();
        return s.map(BasePage::castToSelenideElement).collect(toList());
    }

    private static SelenideElement castToSelenideElement(Object object) {
        if (object instanceof SelenideElement) {
            return (SelenideElement) object;
        }
        return null;
    }

    private static BasePage castToBasePage(Object object) {
        if (object instanceof BasePage) {
            return (BasePage) object;
        }
        return null;
    }

    private Map<String, Object> namedElements;
    private List<SelenideElement> primaryElements;

    @Override
    public void setSelf(SelenideElement self) {
        super.setSelf(self);
        initialize();
    }

    public BasePage initialize() {
        namedElements = readNamedElements();
        primaryElements = readWithWrappedElements();
        return this;
    }

    private Map<String, Object> readNamedElements() {
        checkNamedAnnotations();
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Name.class) != null)
                .peek(this::checkFieldType)
                .collect(toMap(f -> f.getDeclaredAnnotation(Name.class).value(), this::extractFieldValueViaReflection));
    }

    private void checkFieldType(Field f) {
        if (!SelenideElement.class.isAssignableFrom(f.getType())
                && !BasePage.class.isAssignableFrom(f.getType())) {
            checkCollectionFieldType(f);
        }
    }

    private void checkCollectionFieldType(Field f) {
        if (ElementsCollection.class.isAssignableFrom(f.getType())) {
            return;
        } else if (List.class.isAssignableFrom(f.getType())) {
            ParameterizedType listType = (ParameterizedType) f.getGenericType();
            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
            if (SelenideElement.class.isAssignableFrom(listClass) || BasePage.class.isAssignableFrom(listClass)) {
                return;
            }
        }
        throw new IllegalStateException(
                format("Field with annotation @Name have to be with type SelenideElement or List<SelenideElement>.\n" +
                        "If field describes Block it have to be in BasePage.class type.\n" +
                        "Found field with type %s", f.getType()));
    }

    private void checkNamedAnnotations() {
        List<String> list = Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Name.class) != null)
                .map(f -> f.getDeclaredAnnotation(Name.class).value())
                .collect(toList());
        if (list.size() != new HashSet<>(list).size()) {
            throw new IllegalStateException("Too many fields with annotation @Name in same class " + this.getClass().getName());
        }
    }

    private List<SelenideElement> readWithWrappedElements() {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Optional.class) == null)
                .map(this::extractFieldValueViaReflection)
                .flatMap(v -> v instanceof List ? ((List<?>) v).stream() : Stream.of(v))
                .map(BasePage::castToSelenideElement)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private Object extractFieldValueViaReflection(Field field) {
        return Reflection.extractFieldValue(field, this);
    }

    public List<String> getAnyElementsListInnerTexts(String listName) {
        List<SelenideElement> elementsList = getElementsList(listName);
        return elementsList.stream()
                .map(element -> element.getTagName().equals("input")
                        ? element.getValue().trim()
                        : element.innerText().trim()
                )
                .collect(toList());
    }

    public String getAnyElementText(String elementName) {
        return getAnyElementText(getElement(elementName));
    }

    public String getAnyElementText(SelenideElement element) {
        if (element.getTagName().equals("input") || element.getTagName().equals("textarea")) {
            return element.getValue();
        } else {
            return element.getText();
        }
    }


    public List<String> getAnyElementsListTexts(String listName) {
        List<SelenideElement> elementsList = getElementsList(listName);
        return elementsList.stream()
                .map(element -> element.getTagName().equals("input")
                        ? element.getValue()
                        : element.getText()
                )
                .collect(toList());
    }

    public List<SelenideElement> getPrimaryElements() {
        if (primaryElements == null) {
            primaryElements = readWithWrappedElements();
        }
        return new ArrayList<>(primaryElements);
    }

    public final BasePage appeared() {
        isAppeared();
        return this;
    }

    public final BasePage disappeared() {
        isDisappeared();
        return this;
    }

    protected void isAppeared() {
        String timeout = WAITING_APPEAR_TIMEOUT_IN_MILLISECONDS;
        getPrimaryElements().parallelStream().forEach(elem ->
                elem.waitUntil(Condition.appear, Integer.valueOf(timeout)));
        eachForm(BasePage::isAppeared);
    }

    private void eachForm(Consumer<BasePage> func) {
        Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(Optional.class) == null)
                .forEach(f -> {
                    if (BasePage.class.isAssignableFrom(f.getType())) {
                        BasePage BasePage = BaseScenario.getInstance().getPage((Class<? extends BasePage>) f.getType()).initialize();
                        func.accept(BasePage);
                    }
                });
    }

    protected void isDisappeared() {
        getPrimaryElements().parallelStream().forEach(elem ->
                elem.waitWhile(Condition.exist, Integer.valueOf(WAITING_APPEAR_TIMEOUT_IN_MILLISECONDS)));
    }

    public void waitElementsUntil(Condition condition, int timeout, SelenideElement... elements) {
        Spectators.waitElementsUntil(condition, timeout, elements);
    }

    public void waitElementsUntil(Condition condition, int timeout, List<SelenideElement> elements) {
        Spectators.waitElementsUntil(condition, timeout, elements);
    }

    public void waitElementsUntil(Condition condition, int timeout, String... elementNames) {
        List<SelenideElement> elements = Arrays.stream(elementNames)
                .map(name -> namedElements.get(name))
                .flatMap(v -> v instanceof List ? ((List<?>) v).stream() : Stream.of(v))
                .map(BasePage::castToSelenideElement)
                .filter(Objects::nonNull)
                .collect(toList());
        Spectators.waitElementsUntil(condition, timeout, elements);
    }
}