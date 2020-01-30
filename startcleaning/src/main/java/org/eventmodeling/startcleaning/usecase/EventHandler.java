package org.eventmodeling.startcleaning.usecase;

import org.eventmodeling.startcleaning.domain.Event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
