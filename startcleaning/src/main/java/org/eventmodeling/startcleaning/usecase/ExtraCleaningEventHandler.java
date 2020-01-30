package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.CleaningPlanning;

import java.time.LocalDate;

public class ExtraCleaningEventHandler {
    private CleaningPlanning cleaningPlanning;

    public ExtraCleaningEventHandler(CleaningPlanning cleaningPlanning) {
        this.cleaningPlanning = cleaningPlanning;
    }

    public void handle(ExtraCleaningRequested extraCleaningRequested) {
        cleaningPlanning.add(extraCleaningRequested.getDate(), extraCleaningRequested.getRoom());
    }
}
