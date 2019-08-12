package ru.otus.zaikin.framework.steps;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import ru.otus.zaikin.framework.common.BaseScenario;

import java.io.File;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.isIE;
import static java.util.Objects.isNull;
import static ru.otus.zaikin.framework.utils.PropertyLoader.loadPropertyInt;
import static ru.otus.zaikin.framework.utils.PropertyLoader.tryLoadProperty;

@Log4j2
public class BaseMethods {
    protected BaseScenario baseScenario = BaseScenario.getInstance();
    protected static final int DEFAULT_TIMEOUT = loadPropertyInt("waitingCustomElementsTimeout", 15000);

    public String nextWindowHandle() {
        String currentWindowHandle = getWebDriver().getWindowHandle();
        Set<String> windowHandles = getWebDriver().getWindowHandles();
        windowHandles.remove(currentWindowHandle);
        return windowHandles.iterator().next();
    }

    public String getPropertyOrStringVariableOrValue(String propertyNameOrVariableNameOrValue) {
        String propertyValue = tryLoadProperty(propertyNameOrVariableNameOrValue);
        String variableValue = (String) baseScenario.tryGetVar(propertyNameOrVariableNameOrValue);

        boolean propertyCheck = checkResult(propertyValue, "Variable " + propertyNameOrVariableNameOrValue + " from  property file");
        boolean variableCheck = checkResult(variableValue, "Variable of scenario " + propertyNameOrVariableNameOrValue);

        return propertyCheck ? propertyValue : (variableCheck ? variableValue : propertyNameOrVariableNameOrValue);
    }

    private boolean checkResult(String result, String message) {
        if (isNull(result)) {
            log.warn(message + " not found");
            return false;
        }
        log.info(message + " = " + result);
        baseScenario.write(message + " = " + result);
        return true;
    }


    public void deleteFiles(File[] filesToDelete) {
        for (File file : filesToDelete) {
            file.delete();
        }
    }

    public int getRandom(int maxValueInRange) {
        return (int) (Math.random() * maxValueInRange);
    }

    public String getRandCharSequence(int length, String lang) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char symbol = charGenerator(lang);
            builder.append(symbol);
        }
        return builder.toString();
    }

    public char charGenerator(String lang) {
        Random random = new Random();
        if (lang.equals("ru")) {
            return (char) (1072 + random.nextInt(32));
        } else {
            return (char) (97 + random.nextInt(26));
        }
    }

    public boolean isTextMatches(String str, String pattern) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    public String getTranslateNormalizeSpaceText(String expectedText) {
        StringBuilder text = new StringBuilder();
        text.append("//*[contains(translate(normalize-space(text()), ");
        text.append("'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ', ");
        text.append("'abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхчшщъыьэюя'), '");
        text.append(expectedText.toLowerCase());
        text.append("')]");
        return text.toString();
    }

    public void loadPage(String nameOfPage) {
        baseScenario.setCurrentPage(baseScenario.getPage(nameOfPage));
        baseScenario.getCurrentPage().appeared();
    }

    public void cleanField(String nameOfField) {
        SelenideElement valueInput = baseScenario.getCurrentPage().getElement(nameOfField);
        Keys removeKey = isIE() ? Keys.BACK_SPACE : Keys.DELETE;
        do {
            valueInput.doubleClick().sendKeys(removeKey);
        } while (valueInput.getValue().length() != 0);
    }

    public int getCounterFromString(String variableName) {
        return Integer.parseInt(variableName.replaceAll("[^0-9]", ""));
    }

    public void checkPageTitle(String pageTitleName) {
        pageTitleName = getPropertyOrStringVariableOrValue(pageTitleName);
        String currentTitle = getWebDriver().getTitle().trim();
        if (!pageTitleName.equalsIgnoreCase(currentTitle)) throw new RuntimeException("Title is not matched");
    }
}