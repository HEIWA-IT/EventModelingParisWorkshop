package org.eventmodeling.startcleaning.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealEventStoreTest {

    @Test
    void add() {
        new RealEventStore().add(null);

    }
}