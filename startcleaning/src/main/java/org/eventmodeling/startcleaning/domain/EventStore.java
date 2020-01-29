package org.eventmodeling.startcleaning.domain;

import java.util.Collection;

public interface EventStore {
    Collection<Event> all();

    void add(Event event);
}
