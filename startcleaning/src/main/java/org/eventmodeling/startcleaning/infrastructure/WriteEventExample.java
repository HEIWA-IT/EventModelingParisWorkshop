package org.eventmodeling.startcleaning.infrastructure;

import java.time.LocalDate;
import java.util.UUID;
import akka.actor.*;
import akka.event.*;
import eventstore.j.*;
import eventstore.core.*;
import eventstore.akka.tcp.ConnectionActor;
import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.RoomCleaningStartedAdapter;
import org.eventmodeling.startcleaning.usecase.ExtraCleaningRequested;

public class WriteEventExample {

    public static void main(String[] args) {
        RealEventStore realEventStore = new RealEventStore();
        realEventStore.adapter(RoomCleaningStarted.class, new RoomCleaningStartedAdapter());
        realEventStore.add(new RoomCleaningStarted(new Room(301), LocalDate.now()));
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