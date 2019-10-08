package ru.otus.zaikin.calculator;

public class CalculatorService {

    int plus(int a, int b) {
        return a + b;
    }

    int minus(int a, int b) {
        return a - b;
    }

    int multiply(int a, int b) {
        return a * b;
    }

    int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Argument divisor is 0!");
        }
        return a / b;
    }
}
