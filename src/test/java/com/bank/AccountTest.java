package com.bank;

import org.junit.jupiter.api.Test;

import com.bank.model.Account;
import com.bank.model.Cash;
import com.bank.model.Dolar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

public class AccountTest {

    private final Account account = new Account(1, "Kirito");

    @Test
    public void whenAccountPropertiesAreRequiredItReturnsItsValuesCorrectly() {
        assertEquals(1, account.customerId());
        assertEquals("Kirito", account.customerName());
        assertEquals(0, account.balances().size());
        assertNull(account.previousTransaction());
    }
    
    @Test
    public void accountDeposit() {
        Cash cash = new Dolar(500.0, "United States");
        
        List<Cash> expectedBalance = Arrays.asList(cash);
        Double expectedPreviousTransaction = cash.getValue();
        
        account.deposit(cash);
        
        assertEquals(expectedBalance, account.balances());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }
    
    @Test 
    public void accountWithdraw() {
        Cash dolar = new Dolar(1000.0, "United States");
        account.deposit(dolar);

        List<Cash> expectedBalance = Arrays.asList(new Dolar(500.0, "United States"));
        Double expectedPreviousTransaction = -500.0;

        Cash dolarForWithdrawal = new Dolar(500.0, "United States");
        account.withdraw(dolarForWithdrawal);

        assertEquals(expectedBalance.get(0).getValue(), account.searchRequiredCash(dolar).getValue());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }

}
