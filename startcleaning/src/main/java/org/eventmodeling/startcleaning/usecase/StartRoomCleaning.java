package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.usecase.Command;

public class StartRoomCleaning implements Command {
    private final int roomId;

    public StartRoomCleaning(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }
}
