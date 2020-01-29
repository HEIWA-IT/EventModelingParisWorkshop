package org.eventmodeling.startcleaning.usecase;

import org.assertj.core.api.Assertions;
import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.eventmodeling.startcleaning.infrastructure.InMemoryEventStore;
import org.junit.Test;

public class StartCleaningHandlerTest {

    private EventStore eventStore = new InMemoryEventStore();

    @Test
    public void should_generate_room_cleaning_started_event() {
        CommandHandler commandHandler = new StartCleaningHandler(eventStore);

        commandHandler.handle(new StartRoomCleaning(301));

        RoomCleaningStarted roomCleaningStarted = new RoomCleaningStarted(new Room(301));
        Assertions.assertThat(eventStore.all()).containsOnly(roomCleaningStarted);
    }

}