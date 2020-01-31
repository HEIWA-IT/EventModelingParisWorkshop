package org.eventmodeling.startcleaning.infrastructure;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import eventstore.akka.tcp.ConnectionActor;
import eventstore.core.*;
import eventstore.j.EventDataBuilder;
import eventstore.j.WriteEventsBuilder;
import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.EventStore;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class RealEventStore implements EventStore {


    final ActorSystem system   = ActorSystem.create();
    final ActorRef connection  = system.actorOf(ConnectionActor.getProps());
    final ActorRef writeResult = system.actorOf(Props.create(WriteEventExample.WriteResult.class));


    public RealEventStore() {

    }

    @Override
    public Collection<Event> all() {
        return null;
    }

    @Override
    public void add(Event eventd) {
        final EventData event = new EventDataBuilder("my-event")
                .eventId(UUID.randomUUID())
                .data("my event data")
                .metadata("my first event")
                .build();

        final WriteEvents writeEvents = new WriteEventsBuilder("laurent_arc")
                .addEvent(event)
                .expectAnyVersion()
                .build();

        connection.tell(writeEvents, writeResult);
    }

    @Override
    public <T> Set<T> getEventsOfType(Class<T> type) {
        return null;
    }

    public static class WriteResult extends AbstractActor {

        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(WriteEventsCompleted.class, m -> {
                        log.info("range: {}, position: {}", m.numbersRange(), m.position());
                        context().system().terminate();
                    })
                    .match(Status.Failure.class, f -> {
                        final EsException exception = (EsException) f.cause();
                        log.error(exception, exception.toString());
                    })
                    .build();
        }

    }
}

