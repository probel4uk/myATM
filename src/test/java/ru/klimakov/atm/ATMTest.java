package ru.klimakov.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
        atm.deposit(2, 100);
        atm.deposit(3, 500);
        int balance = atm.getBalance();
        Assert.assertEquals(1700, balance);
    }

    @Test
    public void shouldWithdrawByMinimumOfNotes() {
        atm.deposit(10, 1000);
        atm.deposit(10, 100);
        atm.deposit(10, 500);
        atm.deposit(10, 5000);
        int balanceBefore = atm.getBalance();
        Map<Integer, Integer> withdrawal = atm.withdraw(1700);
        Assert.assertEquals(1, withdrawal.get(1000).intValue());
        Assert.assertEquals(1, withdrawal.get(500).intValue());
        Assert.assertEquals(2, withdrawal.get(100).intValue());
        int balanceAfter = atm.getBalance();
        Assert.assertEquals(balanceBefore - 1700, balanceAfter);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAmountCanNotBeWithdraw() {
        atm.deposit(100, 1000);
        atm.withdraw(1700);
    }

    @Test
    public void shouldNotDispenseWhenCantDispense() {
        atm.deposit(10, 1000);
        Assert.assertEquals(10_000, atm.getBalance());
        try {
            atm.withdraw(1700);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        Assert.assertEquals(10_000, atm.getBalance());
    }
}