package com.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountTest {

    private final Account account = new Account(1, "Kirito");

    @Test
    public void whenAccountPropertiesAreRequiredItReturnsItsValuesCorrectly() {
        assertEquals(1, account.customerId());
        assertEquals("Kirito", account.customerName());
        assertEquals(0, account.balance());
        assertNull(account.previousTransaction());
    }
}
