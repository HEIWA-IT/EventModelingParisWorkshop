package org.eventmodeling.startcleaning.infrastructure.filesystem.adapter;

import org.eventmodeling.startcleaning.domain.Event;

public interface EventAdapter<T extends Event, R extends FileEvent> {
    R adapt(T event);
}
