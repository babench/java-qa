package ru.otus.zaikin.yandex.market;

public enum ResultSortEnum {

    PRICE("по цене"),
    QUALITY("по рейтингу"),
    OPINIONS("по отзывам");

    private String criteria;

    public String getCriteria() {
        return criteria;
    }

    ResultSortEnum(String criteria) {
        this.criteria = criteria;
    }
}
