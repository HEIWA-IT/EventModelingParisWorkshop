package org.eventmodeling.startcleaning.infrastructure;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.akka.tcp.ConnectionActor;
import eventstore.core.EventData;
import eventstore.core.WriteEvents;
import eventstore.j.EventDataBuilder;
import eventstore.j.WriteEventsBuilder;
import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.EventAdapter;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.FileEvent;

import java.io.IOException;
import java.util.*;

public class RealEventStore implements EventStore {

    private final ActorSystem system;
    private final ActorRef connection;
    private final ActorRef writeResult;

    private final Map<Class, EventAdapter> allAdapter = new HashMap<>();

    public RealEventStore() {
        system = ActorSystem.create();
        connection = system.actorOf(ConnectionActor.getProps());
        writeResult = system.actorOf(Props.create(WriteEventExample.WriteResult.class));
    }


    public <T> void adapter(Class<T> eventClass, EventAdapter adapter) {
        allAdapter.put(eventClass, adapter);
    }

    @Override
    public Collection<Event> all() {
        return null;
    }

    @Override
    public void add(Event eventd) {
        FileEvent fileEvent = toFileEvent(eventd);
        String json = "";
        try {
            json = writeJsonToFile(fileEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String simpleName = eventd.getClass().getSimpleName();
        final EventData event = new EventDataBuilder(simpleName)
                .eventId(UUID.randomUUID())
                .data(json)
                .build();

        final WriteEvents writeEvents = new WriteEventsBuilder(simpleName)
                .addEvent(event)
                .expectAnyVersion()
                .build();

        connection.tell(writeEvents, writeResult);
    }

    private String writeJsonToFile(FileEvent fileEvent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(fileEvent);
    }

    private FileEvent toFileEvent(Event event) {
        EventAdapter eventAdapter = allAdapter.get(event.getClass());
        return eventAdapter.adapt(event);
    }

    @Override
    public <T> Set<T> getEventsOfType(Class<T> type) {
        return null;
    }
}

