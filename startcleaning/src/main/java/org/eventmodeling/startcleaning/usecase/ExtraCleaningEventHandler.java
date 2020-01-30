package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.CleaningPlanning;

public class ExtraCleaningEventHandler implements EventHandler<ExtraCleaningRequested> {
    private CleaningPlanning cleaningPlanning;

    public ExtraCleaningEventHandler(CleaningPlanning cleaningPlanning) {
        this.cleaningPlanning = cleaningPlanning;
    }

    @Override
    public void handle(ExtraCleaningRequested extraCleaningRequested) {
        cleaningPlanning.add(extraCleaningRequested.getDate(), extraCleaningRequested.getRoom());
    }
}
