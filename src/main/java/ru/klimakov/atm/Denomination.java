package ru.klimakov.atm;

public enum Denomination {
    FIVE_THOUSANDS(5000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    ONE_HUNDRED(100);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
