package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdGeneratorTest {
    private final IdGenerator idGenerator = new IdGenerator();

    @Test
    public void whenIdGeneratorIsCreatedItFirstIdEqualsToZero() {
        Integer expectedId = 0;
        Integer actualId = idGenerator.id();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void whenIdGeneratorIsRequiredToSendAnIdItsNewIdEqualsToOne() {
        Integer expectedId = 1;
        Integer actualId = idGenerator.nextId();

        assertEquals(expectedId, actualId);
    }
}