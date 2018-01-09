package ru.klimakov.atm;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static ru.klimakov.atm.Denomination.*;

/* ATM должен
• принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
• выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
• выдавать сумму остатка денежных средств
 */
public class ATMTest {
    private ATM atm;

    @Before
    public void setUp() throws Exception {
        atm = new ATM();
    }

    @Test
    public void shouldDepositNotesOfDifferentDenominations() {
        atm.deposit(2, ONE_HUNDRED);
        atm.deposit(3, FIVE_HUNDREDS);
        int balance = atm.getBalance();
        assertEquals(1700, balance);
    }

    @Test
    public void shouldWithdrawByMinimumOfNotes() {
        atm.deposit(10, ONE_THOUSAND);
        atm.deposit(10, ONE_HUNDRED);
        atm.deposit(10, FIVE_HUNDREDS);
        atm.deposit(10, FIVE_THOUSANDS);
        int balanceBefore = atm.getBalance();
        Map<Denomination, Integer> withdrawal = atm.withdraw(1700);
        assertEquals(1, withdrawal.get(ONE_THOUSAND).intValue());
        assertEquals(1, withdrawal.get(FIVE_HUNDREDS).intValue());
        assertEquals(2, withdrawal.get(ONE_HUNDRED).intValue());
        int balanceAfter = atm.getBalance();
        assertEquals(balanceBefore - 1700, balanceAfter);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAmountCanNotBeWithdraw() {
        atm.deposit(10, ONE_THOUSAND);
        atm.withdraw(1700);
    }

    @Test
    public void shouldNotDispenseWhenCantDispense() {
        atm.deposit(10, ONE_THOUSAND);
        assertEquals(10_000, atm.getBalance());
        try {
            atm.withdraw(1700);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(10_000, atm.getBalance());
    }
}