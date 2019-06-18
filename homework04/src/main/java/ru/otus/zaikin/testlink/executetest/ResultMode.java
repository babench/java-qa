package ru.otus.zaikin.testlink.executetest;

public enum ResultMode {
    Current("fastExec"),
    Next("fastExecNext");
    private String valueOnForm;

    public String getValueOnForm() {
        return valueOnForm;
    }

    ResultMode(String valueOnForm) {
        this.valueOnForm = valueOnForm;
    }
}
