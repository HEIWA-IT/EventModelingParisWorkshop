package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StartCleaningHandler implements CommandHandler<StartRoomCleaning> {
    private final EventStore eventStore;

    public StartCleaningHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void handle(StartRoomCleaning startRoomCleaning) {
        Room room = startRoomCleaning.getRoom();
        if (isNotPlannedToday(room)) {
            throw new NoCleaningRequestFound(room);
        }
        this.eventStore.add(new RoomCleaningStarted(room, LocalDate.now()));
    }

    private boolean isNotPlannedToday(Room room) {
        Set<ExtraCleaningRequested> events = eventStore.getEventsOfType(ExtraCleaningRequested.class);
        return events
                .stream()
                .noneMatch(it -> it.getRoom().equals(room)
                        && it.getDate().equals(LocalDate.now()));
    }

}
