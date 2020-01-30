package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.CleaningPlanning;
import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;

import java.time.LocalDate;

public class StartCleaningHandler implements CommandHandler<StartRoomCleaning> {
    private final EventStore eventStore;
    private final CleaningPlanning cleaningPlanning;

    public StartCleaningHandler(EventStore eventStore, CleaningPlanning cleaningPlanning) {
        this.cleaningPlanning = cleaningPlanning;
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
        return !cleaningPlanning.getRoomsToClean(LocalDate.now()).contains(room);
    }

}
