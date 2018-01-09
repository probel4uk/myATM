package ru.klimakov.atm;

import java.util.*;

public class ATM {

    private SortedMap<Integer, Integer> dispensers = new TreeMap<>(Collections.reverseOrder());

    public void deposit(int notes, int denomination) {
        int value = dispensers.getOrDefault(denomination, 0) + notes;
        dispensers.put(denomination, value);
    }

    public int getBalance() {
        return dispensers.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
    }

    public Map<Integer, Integer> withdraw(final int amount) {
        Map<Integer, Integer> withdrawal = new HashMap<>();
        int remain = amount;
        for (Integer denomination: dispensers.keySet()) {
            int notes = dispensers.get(denomination);
            int notesNeeded = remain / denomination;
            int notesDispensed = notes > notesNeeded
                    ? notesNeeded
                    : notes;
            withdrawal.put(denomination, notesDispensed);
            remain -= denomination * notesDispensed;
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
