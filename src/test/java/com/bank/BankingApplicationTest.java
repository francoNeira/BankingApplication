package com.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.accounts.Account;
import com.bank.model.accounts.SavingAccount;
import com.bank.model.cash.Cash;
import com.bank.model.cash.Dolar;
import com.bank.model.cash.Euro;
import com.bank.model.cash.Peso;

import utils.IdGenerator;

public class BankingApplicationTest {
    private BankingApplication application;
    private String kiritoCustomerName;
    private String asunaCustomerName;
    private Cash peso;
    private Account account1;
    private Account account2;
    private IdGenerator idGenerator;

    @BeforeEach
    public void setUp() {
        application = new BankingApplication();
        kiritoCustomerName = "Kirito";
        asunaCustomerName = "Asuna";
        idGenerator = new IdGenerator();
        peso = new Peso(0.0, "Argentina");
        account1 = new SavingAccount(idGenerator.nextId(), kiritoCustomerName, peso, "Argentina");
        account2 = new SavingAccount(idGenerator.nextId(), asunaCustomerName, peso, "Brasil");
    }

    @Test
    public void whenBankingApplicationStartsItHasNotRegisteredAccounts() {
        assertTrue(isEmpty(application.accounts()));
    }

    @Test
    public void whenAPersonRegistersAnAccountTheApplicationCreatesItCorrectlyAndReturnsItsId() {
        Integer expectedId = 1;
		Integer actualId = application.register(account1);

        assertEquals(expectedId, actualId);
    }

    @Test
    public void whenAPersonRegistersAnAccountTheApplicationRegistersItCorrectly() {
        application.register(account1);

        assertFalse(isEmpty(application.accounts()));
    }

    @Test
    public void whenTwoPersonsRegistersAccountsTheApplicationCreatesItCorrectlyWithDifferentIds() {
        Integer firstId = application.register(account1);
        Integer secondId = application.register(account2);

        assertNotEquals(firstId, secondId);
    }

    @Test
    public void whenAnAccountIsSearchedByIdTheApplicationFindsItAndReturnsItCorrectly() {
        Integer newId = application.register(account1);
        Cash cash = new Dolar(0.0, "United States");
        
        Integer expectedId = 1;
        String expectedName = "Kirito";
        List<Cash> expectedBalance = Arrays.asList(cash);
        Double expectedPreviousTransaction = 0.0;

        Account newAccount = application.findById(newId);
        newAccount.deposit(cash);
        
        assertEquals(expectedId, newAccount.customerId());
        assertEquals(expectedName, newAccount.customerName());
        assertEquals(expectedBalance, newAccount.balances());
        assertEquals(expectedPreviousTransaction, newAccount.previousTransaction());
    }

    @Test
    public void whenTwoPersonsRegisterAccountsTheApplicationRegistersItCorrectly() {
        Integer firstId = application.register(account1);
        Integer secondId = application.register(account2);

        Account firstAccount = application.findById(firstId);
        Account secondAccount = application.findById(secondId);

        assertTrue(contains(application.accounts(), firstAccount, secondAccount));
        assertFalse(isEmpty(application.accounts()));
    }

    @Test
    public void whenAPersonDoesADepositTheApplicationUpdatesHisAccountCorrectly() {
        Integer customerId = application.register(account1);
        Account account = application.findById(customerId);

        Cash dolar = new Dolar(1000.0, "United States");
        
        application.doDeposit(account, dolar);

        assertEquals(dolar.getValue(), account.searchRequiredCash(dolar).getValue());
        assertEquals(dolar.getValue(), account.previousTransaction());
    }

    @Test
    public void whenAPersonDoesAWithdrawalTheApplicationUpdatesHisAccountCorrectly() {
        Integer customerId = application.register(account1);
        Account account = application.findById(customerId);
        Cash dolar = new Dolar(1000.0, "United States");
        application.doDeposit(account, dolar);

        List<Cash> expectedBalance = Arrays.asList(new Dolar(500.0, "United States"));
        Double expectedPreviousTransaction = -500.0;

        Cash dolarForWithdrawal = new Dolar(500.0, "United States");
        application.doWithdrawal(account, dolarForWithdrawal);

        assertEquals(expectedBalance.size(), account.balances().size());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }

    @Test
    public void whenAPersonTriesToDoAWithdrawalWhenHeHasNotEnoughMoneyInAccountTheApplicationUnablesIt() {
        Integer customerId = application.register(account1);
        Account account = application.findById(customerId);

        Cash euro = new Euro(500.0, "European Union");
        assertThrows(NotEnoughBalance.class, () -> application.doWithdrawal(account, euro));
    }


    // Auxiliary methods

    /**
     * Returns true if the list contains no elements.
     *
     * @return true if the list contains no elements.
     */
    private <T> boolean isEmpty(List<T> list) {
        return list.isEmpty();
    }

    /**
     * Returns true if the list contains the specified element.
     *
     * @param elems elements whose presence in the list is to be tested.
     * @return true if the list contains the specified element.
     */
    private <T> boolean contains(List<T> list, @SuppressWarnings("unchecked") T... elems) {
        return Arrays.stream(elems).allMatch(list::contains);
    }
}

