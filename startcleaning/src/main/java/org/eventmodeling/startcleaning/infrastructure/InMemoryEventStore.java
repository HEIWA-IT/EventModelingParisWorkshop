package org.eventmodeling.startcleaning.infrastructure;

import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.EventStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryEventStore implements EventStore {

    private List<Event> events = new ArrayList<>();

    public Collection<Event> all() {
        return events;
    }

    @Override
    public void add(Event event) {
        events.add(event);
    }

    @Override
    public  <T> Set<T> getEventsOfType(Class<T> type) {
        return all().stream()
                .filter(it -> it.getClass() == type)
                .map(it -> (T) it)
                .collect(Collectors.toSet());
    }
}
