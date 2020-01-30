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
        System.out.println("Given :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
        CommandHandler commandHandler = new StartCleaningHandler(eventStore);


        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);
        commandHandler.handle(command);


        System.out.println("Then :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
        RoomCleaningStarted roomCleaningStarted = new RoomCleaningStarted(new Room(301));
        Assertions.assertThat(eventStore.all()).containsOnly(roomCleaningStarted);
    }

}