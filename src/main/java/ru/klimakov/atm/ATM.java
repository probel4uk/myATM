package ru.klimakov.atm;

import java.util.*;

public class ATM {

    private Map<Denomination, Integer> dispensers = new HashMap<>();

    public void deposit(int notes, Denomination denomination) {
        int value = dispensers.getOrDefault(denomination, 0) + notes;
        dispensers.put(denomination, value);
        System.out.println("5555555555");
        System.out.println("555555ввв555");
        System.out.println("555555ввв555");
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
            int notes = dispensers.getOrDefault(denomination, 0);
            if (notes > 0) {
                int notesNeeded = remain / denomination.getValue();
                int notesToDispense = Math.min(notes, notesNeeded);
                withdrawal.put(denomination, notesToDispense);
                remain -= denomination.getValue() * notesToDispense;
            }
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
