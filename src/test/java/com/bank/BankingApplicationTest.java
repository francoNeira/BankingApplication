package com.bank;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankingApplicationTest {
    private BankingApplication application;
    private String kiritoCustomerName;
    private String asunaCustomerName;

    @BeforeEach
    public void setUp() {
        application = new BankingApplication();
        kiritoCustomerName = "Kirito";
        asunaCustomerName = "Asuna";
    }

    @Test
    public void whenBankingApplicationStartsItHasNotRegisteredAccounts() {
        assertTrue(isEmpty(application.accounts()));
    }

    @Test
    public void whenAPersonRegistersAnAccountTheApplicationCreatesItCorrectlyAndReturnsItsId() {
        Integer expectedId = 1;
        Integer actualId = application.register(kiritoCustomerName);

        assertEquals(expectedId, actualId);
    }

    @Test
    public void whenAPersonRegistersAnAccountTheApplicationRegistersItCorrectly() {
        application.register(kiritoCustomerName);

        assertFalse(isEmpty(application.accounts()));
    }

    @Test
    public void whenTwoPersonsRegistersAccountsTheApplicationCreatesItCorrectlyWithDifferentIds() {
        Integer firstId = application.register(kiritoCustomerName);
        Integer secondId = application.register(asunaCustomerName);

        assertNotEquals(firstId, secondId);
    }

    @Test
    public void whenAnAccountIsSearchedByIdTheApplicationFindsItAndReturnsItCorrectly() {
        Integer newId = application.register(kiritoCustomerName);

        Integer expectedId = 1;
        String expectedName = "Kirito";
        Double expectedBalance = 0.0;
        Double expectedPreviousTransaction = null;

        Account newAccount = application.findById(newId);

        assertEquals(expectedId, newAccount.customerId());
        assertEquals(expectedName, newAccount.customerName());
        assertEquals(expectedBalance, newAccount.balance());
        assertEquals(expectedPreviousTransaction, newAccount.previousTransaction());
    }

    @Test
    public void whenTwoPersonsRegisterAccountsTheApplicationRegistersItCorrectly() {
        Integer firstId = application.register(kiritoCustomerName);
        Integer secondId = application.register(asunaCustomerName);

        Account firstAccount = application.findById(firstId);
        Account secondAccount = application.findById(secondId);

        assertTrue(contains(application.accounts(), firstAccount, secondAccount));
        assertFalse(isEmpty(application.accounts()));
    }

    @Test
    public void whenAPersonDoesADepositTheApplicationUpdatesHisAccountCorrectly() {
        Integer customerId = application.register(kiritoCustomerName);
        Account account = application.findById(customerId);

        Double amount = 1000.0;
        application.doDeposit(account, amount);

        assertEquals(amount, account.balance());
        assertEquals(amount, account.previousTransaction());
    }

    @Test
    public void whenAPersonDoesAWithdrawalTheApplicationUpdatesHisAccountCorrectly() {
        Integer customerId = application.register(kiritoCustomerName);
        Account account = application.findById(customerId);
        application.doDeposit(account, 1000.0);

        Double expectedBalance = 500.0;
        Double expectedPreviousTransaction = -500.0;

        Double amount = 500.0;
        application.doWithdrawal(account, amount);

        assertEquals(expectedBalance, account.balance());
        assertEquals(expectedPreviousTransaction, account.previousTransaction());
    }

    @Test
    public void whenAPersonTriesToDoAWithdrawalWhenHeHasNotEnoughMoneyInAccountTheApplicationUnablesIt() {
        Integer customerId = application.register(kiritoCustomerName);
        Account account = application.findById(customerId);

        Double amount = 500.0;
        assertThrows(NotEnoughBalance.class, () -> application.doWithdrawal(account, amount));
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
    private <T> boolean contains(List<T> list, T... elems) {
        return Arrays.stream(elems).allMatch(list::contains);
    }
}
