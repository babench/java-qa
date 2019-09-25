package ru.otus.zaikin.calculator;

public class CalculatorController {

    private final CalculatorService calculatorService;
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public String calc(CalculatorOperation operation, Integer a, Integer b) {

        switch (operation) {
            case PLUS:
                return String.valueOf(calculatorService.plus(a, b));
            case MINUS:
                return String.valueOf(calculatorService.minus(a, b));
            case MULTIPLY:
                return String.valueOf(calculatorService.multiply(a, b));
            case DIVIDE:
                return String.valueOf(calculatorService.divide(a, b));
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}