package com.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.model.accounts.CheckingAccount;
import com.bank.model.cash.Cash;
import com.bank.model.cash.Peso;
import com.bank.model.cash.Real;

import utils.IdGenerator;

public class CheckingAccountTest {
	private Cash peso = new Peso(0.0, "México");
	private Cash amountOfOverdraft = new Peso(500.0, "México");
    private CheckingAccount account = new CheckingAccount(new IdGenerator().nextId(), "Charly", peso, amountOfOverdraft, 2, "México");

    @Test
    public void whenAccountPropertiesAreRequiredItReturnsItsValuesCorrectly() {
        assertEquals(1, account.customerId());
        assertEquals("Charly", account.customerName());
        assertEquals(0, account.balances().size());
        assertNull(account.previousTransaction());
    }
    
    
    @Test
    public void generateOverdraftCheck() throws Exception{
    	account.generateOverdraftCheck("payment of services");
    	
    	assertEquals(500.0, account.getDebt().get(0).getValue());
    	assertEquals(1, account.getNumberOfOverdrafts());
    }
     
    @Test
    public void accountDeposit() {
        Cash cash = new Real(500.0);
        
        List<Cash> expectedBalance = Arrays.asList(cash);
        Double expectedPreviousTransaction = cash.getValue();
        
        account.deposit(cash);
        
        assertEquals(expectedBalance, account.balances());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }
    
    @Test 
    public void accountWithdraw() {
        Cash dolar = new Real(1000.0);
        account.deposit(dolar);

        List<Cash> expectedBalance = Arrays.asList(new Real(500.0));
        Double expectedPreviousTransaction = -500.0;

        Cash dolarForWithdrawal = new Real(500.0);
        account.withdraw(dolarForWithdrawal);

        assertEquals(expectedBalance.get(0).getValue(), account.searchRequiredCash(dolar).getValue());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }
}
