package ru.otus.zaikin.yandex.market;

public enum FilterEnum {
    PRODUCER("Производитель"),
    PRODUCT_LINE("Линейка");

    private final String legend;

    public String getLegend() {
        return legend;
    }

    FilterEnum(String legend) {
        this.legend = legend;
    }
}
