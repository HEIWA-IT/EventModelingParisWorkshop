package org.eventmodeling.startcleaning.infrastructure.filesystem.adapter;

import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;

public class RoomCleaningStartedAdapter implements EventAdapter<RoomCleaningStarted, FileRoomCleaningStarted> {
    @Override
    public FileRoomCleaningStarted adapt(RoomCleaningStarted event) {
        return new FileRoomCleaningStarted(event.getRoom().getId(), event.getCleaningDate().toString());
    }

}
