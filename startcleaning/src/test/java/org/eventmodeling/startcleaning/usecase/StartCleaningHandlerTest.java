package org.eventmodeling.startcleaning.usecase;

import org.assertj.core.api.Assertions;
import org.eventmodeling.startcleaning.domain.*;
import org.eventmodeling.startcleaning.infrastructure.InMemoryEventStore;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class StartCleaningHandlerTest {

    private EventStore eventStore;
    private CommandHandler commandHandler;
    private ExtraCleaningEventHandler eventHandler;

    @Before
    public void setUp() {
        eventStore = new InMemoryEventStore();
        CleaningPlanning cleaningPlanning = new CleaningPlanning(); // TODO: 2020-01-30 Add repository
        eventHandler = new ExtraCleaningEventHandler(cleaningPlanning);
        commandHandler = new StartCleaningHandler(eventStore);
    }

    @Test
    public void should_fail_generate_room_cleaning_started_event() {
        printGiven();

        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);

        NoCleaningRequestFound exception = new NoCleaningRequestFound(new Room(301));
        printThenException(exception);
        Assertions.assertThatThrownBy(() -> commandHandler.handle(command))
                .isExactlyInstanceOf(exception.getClass())
                .hasMessage(exception.getMessage());
    }

    @Test
    public void should_fail_if_cleaning_is_not_planned_today() {
        ExtraCleaningRequested extraCleaningRequested = new ExtraCleaningRequested(302, LocalDate.now());
        eventStore.add(extraCleaningRequested);
        eventHandler.handle(extraCleaningRequested); // TODO: 2020-01-30 it should be done by the event store, a middleware, a bus ?

        printGiven();

        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);


        NoCleaningRequestFound exception = new NoCleaningRequestFound(new Room(301));
        printThenException(exception);
        Assertions.assertThatThrownBy(() -> commandHandler.handle(command))
                .isExactlyInstanceOf(exception.getClass())
                .hasMessage(exception.getMessage());

    }


    @Test
    public void should_generate_room_cleaning_started_event() {
        ExtraCleaningRequested extraCleaningRequested = new ExtraCleaningRequested(301, LocalDate.now());
        eventStore.add(extraCleaningRequested);
        eventHandler.handle(extraCleaningRequested); // TODO: 2020-01-30 it should be done by the event store, a middleware, a bus ?

        printGiven();


        StartRoomCleaning command = new StartRoomCleaning(new Room(301));
        System.out.println("When :");
        System.out.println("    " + command);
        commandHandler.handle(command);


        printThen();
        RoomCleaningStarted roomCleaningStarted = new RoomCleaningStarted(new Room(301), LocalDate.now());
        Assertions.assertThat(eventStore.all()).containsExactly(extraCleaningRequested, roomCleaningStarted);
    }

    private void printGiven() {
        System.out.println("Given :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
    }

    private void printThenException(NoCleaningRequestFound exception) {
        System.out.println("Then :");
        System.out.println("    " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
    }

    private void printThen() {
        System.out.println("Then :");
        eventStore.all().forEach(it -> System.out.println("    " + it));
    }

}