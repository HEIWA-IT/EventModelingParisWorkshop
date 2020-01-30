package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StartCleaningHandler implements CommandHandler<StartRoomCleaning> {
    private final EventStore eventStore;

    public StartCleaningHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void handle(StartRoomCleaning startRoomCleaning) {
        if (this.eventStore.all().stream().noneMatch(it -> it instanceof ExtraCleaningRequested)) {
            throw new NoCleaningRequestFound(startRoomCleaning.getRoom());
        }
        this.eventStore.add(new RoomCleaningStarted(startRoomCleaning.getRoom(), LocalDate.now()));


    }

}
