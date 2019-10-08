package ru.otus.zaikin.calculator;

public class DivideToZeroException extends ArithmeticException {
    public DivideToZeroException() {
        super("Argument divisor is 0!");
    }
}
