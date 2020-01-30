package org.eventmodeling.startcleaning.usecase;

import org.assertj.core.api.Assertions;
import org.eventmodeling.startcleaning.domain.CleaningPlanning;
import org.eventmodeling.startcleaning.domain.Room;
import org.junit.Test;

import java.time.LocalDate;

public class ExtraCleaningEventHandlerTest {

    private final CleaningPlanning cleaningPlanning = new CleaningPlanning();
    private ExtraCleaningEventHandler handler = new ExtraCleaningEventHandler(this.cleaningPlanning);

    @Test
    public void cleaning_calendar_should_be_empty_at_first() {
        Assertions.assertThat(new CleaningPlanning().getRoomsToClean(LocalDate.now())).isEmpty();
    }

    @Test
    public void should_add_dirty_room_when_cleaning_is_requested() {
        LocalDate date = LocalDate.now();
        handler.handle(new ExtraCleaningRequested(301, date));
        handler.handle(new ExtraCleaningRequested(302, date));
        handler.handle(new ExtraCleaningRequested(303, date));
        handler.handle(new ExtraCleaningRequested(306, date));
        handler.handle(new ExtraCleaningRequested(307, date));

        Assertions.assertThat(this.cleaningPlanning.getRoomsToClean(date)).containsExactlyInAnyOrder(
                new Room(301),
                new Room(302),
                new Room(303),
                new Room(306),
                new Room(307)
        );
    }

    @Test
    public void should_add_dirty_room_when_cleaning_is_requested_on_several_days() {
        LocalDate date = LocalDate.now();
        handler.handle(new ExtraCleaningRequested(306, date.minusDays(5)));
        handler.handle(new ExtraCleaningRequested(301, date.plusDays(2)));
        handler.handle(new ExtraCleaningRequested(302, date));
        handler.handle(new ExtraCleaningRequested(303, date));
        handler.handle(new ExtraCleaningRequested(307, date));

        Assertions.assertThat(this.cleaningPlanning.getRoomsToClean(date.minusDays(5))).containsExactlyInAnyOrder(
                new Room(306)
        );
        Assertions.assertThat(this.cleaningPlanning.getRoomsToClean(date)).containsExactlyInAnyOrder(
                new Room(302),
                new Room(303),
                new Room(307)
        );
        Assertions.assertThat(this.cleaningPlanning.getRoomsToClean(date.plusDays(2))).containsExactlyInAnyOrder(
                new Room(301)
        );
    }

}
