package ru.klimakov.atm;

import java.util.*;

public class ATM {

    private Map<Denomination, Integer> dispensers = new HashMap<>();

    public void deposit(int notes, Denomination denomination) {
        int value = dispensers.getOrDefault(denomination, 0) + notes;
        dispensers.put(denomination, value);
    }

    public int getBalance() {
        return dispensers.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    public Map<Denomination, Integer> withdraw(final int amount) {
        Map<Denomination, Integer> withdrawal = new HashMap<>();
        int remain = amount;
        for (Denomination denomination: Denomination.values()) {
            int notes = dispensers.get(denomination);
            int notesNeeded = remain / denomination.getValue();
            int notesDispensed = notes > notesNeeded
                    ? notesNeeded
                    : notes;
            withdrawal.put(denomination, notesDispensed);
            remain -= denomination.getValue() * notesDispensed;
        }
        if (remain > 0) {
            throw new RuntimeException(String.format("Can not dispense amount %d by dispenser %s", amount, dispensers));
        }
        withdrawal.forEach((denomination, notesDispensed) ->
            dispensers.compute(denomination, (key, notes ) -> notes - notesDispensed)
        );
        return withdrawal;
    }
}
