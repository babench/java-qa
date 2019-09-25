package ru.otus.zaikin.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.common.ListOrSingle;
import ru.otus.zaikin.calculator.CalculatorController;
import ru.otus.zaikin.calculator.CalculatorOperation;
import ru.otus.zaikin.calculator.CalculatorService;

import java.io.IOException;

public class CalcHelper implements Helper<Object> {
    @Override
    public Object apply(Object context, Options options) throws IOException {
        return new CalculatorController(
                new CalculatorService()).calc(
                CalculatorOperation.valueOf(((String) ((ListOrSingle) context).get(0)).toUpperCase()),
                Integer.valueOf(String.valueOf(((ListOrSingle) options.params[0]).getFirst())),
                Integer.valueOf(String.valueOf(((ListOrSingle) options.params[1]).getFirst()))
        );
    }
}