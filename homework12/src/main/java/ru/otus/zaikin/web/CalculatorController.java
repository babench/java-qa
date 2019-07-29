package ru.otus.zaikin.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.zaikin.service.CalculatorService;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/calc")
    @ApiOperation("Simple calculator")
    public String calc(@RequestParam("operation") CalculatorOperation operation, @RequestParam("a")
    @ApiParam("First operand") Integer a, @RequestParam("b") @ApiParam("Second operand") Integer b) {

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