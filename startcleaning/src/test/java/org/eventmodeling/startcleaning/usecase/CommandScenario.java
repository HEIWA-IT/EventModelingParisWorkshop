package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.Event;

import java.util.Collection;

public class CommandScenario {
    private final Collection<Event> givenEvents;
    private final Command command;
    private final Collection<Event> expectedEvents;

    public CommandScenario(Collection<Event> givenEvents, Command command, Collection<Event> expectedEvents) {
        this.givenEvents = givenEvents;
        this.command = command;
        this.expectedEvents = expectedEvents;
    }

    public static class Builder {
        private Collection<Event> givenEvents;
        private Command command;
        private Collection<Event> expectedEvents;

        public Builder given(Collection<Event> givenEvents) {
            this.givenEvents = givenEvents;
            return this;
        }

        public Builder when(Command command) {
            this.command = command;
            return this;
        }

        public Builder then(Collection<Event> expectedEvents) {
            this.expectedEvents = expectedEvents;
            return this;
        }

        public CommandScenario createCommandScenario() {
            return new CommandScenario(givenEvents, command, expectedEvents);
        }
    }
}
