package org.eventmodeling.startcleaning.infrastructure;

import eventstore.core.EventData;
import eventstore.core.WriteEvents;
import eventstore.j.EventDataBuilder;
import eventstore.j.WriteEventsBuilder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RealEventStoreTest {

    @Test
    void add() {
        new RealEventStore().add(null);
    }
}