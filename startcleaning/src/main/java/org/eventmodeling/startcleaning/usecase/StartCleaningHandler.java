package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;

class StartCleaningHandler implements CommandHandler<StartRoomCleaning> {
    private final EventStore eventStore;

    public StartCleaningHandler(EventStore eventStore) {

        this.eventStore = eventStore;
    }

    @Override
    public void handle(StartRoomCleaning startRoomCleaning) {
        this.eventStore.add(new RoomCleaningStarted(new Room(startRoomCleaning.getRoomId())));

    }
}
