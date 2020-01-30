package org.eventmodeling.startcleaning.usecase;

import org.assertj.core.api.Assertions;
import org.eventmodeling.startcleaning.domain.CleaningPlanning;
import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.eventmodeling.startcleaning.infrastructure.InMemoryEventStore;
import org.junit.Before;
import org.junit.Test;

import java.beans.EventHandler;
import java.time.LocalDate;

public class StartCleaningHandlerTest {

    private EventStore eventStore;
    private CommandHandler commandHandler;
    private ExtraCleaningEventHandler eventHandler;

    @Before
    public void setUp() {
        eventStore = new InMemoryEventStore();
        CleaningPlanning cleaningPlanning = new CleaningPlanning();
        eventHandler = new ExtraCleaningEventHandler(cleaningPlanning);
        commandHandler = new StartCleaningHandler(eventStore, cleaningPlanning);
    }

    @Test
    public void should_fail_generate_room_cleaning_started_event() {
        System.out.println("Given :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
        CommandHandler commandHandler = new StartCleaningHandler(eventStore, new CleaningPlanning());

        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);

        NoCleaningRequestFound exception = new NoCleaningRequestFound(new Room(301));
        System.out.println("Then :");
        System.out.println("    " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
        Assertions.assertThatThrownBy(() -> commandHandler.handle(command))
                .isExactlyInstanceOf(exception.getClass())
                .hasMessage(exception.getMessage());
    }

    @Test
    public void should_fail() {
        System.out.println("Given :");
        ExtraCleaningRequested extraCleaningRequested = new ExtraCleaningRequested(302, LocalDate.now());
        eventStore.add(extraCleaningRequested);
        eventStore.all().forEach(it -> System.out.println("    " + it));
        CommandHandler commandHandler = new StartCleaningHandler(eventStore, new CleaningPlanning());

        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);


        NoCleaningRequestFound exception = new NoCleaningRequestFound(new Room(301));
        System.out.println("Then :");
        System.out.println("    " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
        Assertions.assertThatThrownBy(() -> commandHandler.handle(command))
                .isExactlyInstanceOf(exception.getClass())
                .hasMessage(exception.getMessage());

    }


    @Test
    public void should_generate_room_cleaning_started_event() {
        ExtraCleaningRequested extraCleaningRequested = new ExtraCleaningRequested(301, LocalDate.now());
        eventStore.add(extraCleaningRequested);
        eventHandler.handle(extraCleaningRequested); // TODO: 2020-01-30 it should be done by the event store ?

        System.out.println("Given :");
        eventStore.all().forEach(it -> System.out.println("    " + it));


        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);
        commandHandler.handle(command);


        System.out.println("Then :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
        RoomCleaningStarted roomCleaningStarted = new RoomCleaningStarted(new Room(301), LocalDate.now());
        Assertions.assertThat(eventStore.all()).containsExactly(extraCleaningRequested, roomCleaningStarted);
    }

}