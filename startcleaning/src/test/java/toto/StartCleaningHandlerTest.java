package toto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class StartCleaningHandlerTest {

    private EventStore eventStore = new InMemoryEventStore();

    @Test
    public void should_generate_room_cleaning_started_event() {
        CommandHandler commandHandler = new StartCleaningHandler(eventStore);

        commandHandler.handle(new StartRoomCleaning(301));

        RoomCleaningStarted roomCleaningStarted = new RoomCleaningStarted(new Room(301));
        Assertions.assertThat(eventStore.all()).containsOnly(roomCleaningStarted);
    }

    interface EventStore {
        Collection<Event> all();

        void add(Event event);
    }

    interface Event {
    }

    private static class StartCleaningHandler implements CommandHandler<StartRoomCleaning> {
        private final EventStore eventStore;

        public StartCleaningHandler(EventStore eventStore) {

            this.eventStore = eventStore;
        }

        @Override
        public void handle(StartRoomCleaning startRoomCleaning) {
            this.eventStore.add(new RoomCleaningStarted(new Room(startRoomCleaning.getRoomId())));

        }
    }

    private static class RoomCleaningStarted implements Event {
        private final Room room;

        public RoomCleaningStarted(Room room) {
            this.room = room;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RoomCleaningStarted that = (RoomCleaningStarted) o;
            return Objects.equals(room, that.room);
        }

        @Override
        public int hashCode() {
            return Objects.hash(room);
        }

        @Override
        public String toString() {
            return "RoomCleaningStarted{" +
                    "room=" + room +
                    '}';
        }
    }

    private class InMemoryEventStore implements EventStore {

        private List<Event> events = new ArrayList<>();

        public Collection<Event> all() {
            return events;
        }

        @Override
        public void add(Event event) {
            events.add(event);
        }
    }
}