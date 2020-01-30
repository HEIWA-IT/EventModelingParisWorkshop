package org.eventmodeling.startcleaning.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface EventStore {
    Collection<Event> all();

    void add(Event event);

    <T> Set<T> getEventsOfType(Class<T> type);
}
