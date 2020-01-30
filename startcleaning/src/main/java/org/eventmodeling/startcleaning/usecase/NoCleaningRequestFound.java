package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.Room;

public class NoCleaningRequestFound extends RuntimeException {
    public NoCleaningRequestFound(Room room) {
        super("No cleaning requested today for " + room);
    }
}
