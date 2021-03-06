package com.github.newlight77;

import akka.actor.*;
import eventstore.akka.Settings;
import eventstore.akka.tcp.ConnectionActor;
import eventstore.core.EventData;
import eventstore.core.WriteEvents;
import eventstore.core.WriteEventsCompleted;
import eventstore.core.WriteResult;
import eventstore.j.EventDataBuilder;
import eventstore.j.SettingsBuilder;
import eventstore.j.WriteEventsBuilder;

import java.net.InetSocketAddress;
import java.util.UUID;

public class AppMain {

    public static void main(String[] args) {
        System.out.println("Hello");

        final Settings settings = new SettingsBuilder()
                .address(new InetSocketAddress("127.0.0.1", 1113))
                .defaultCredentials("admin", "changeit")
                .build();

        final ActorSystem system   = ActorSystem.create();
        final ActorRef connection  = system.actorOf(ConnectionActor.getProps(settings));
        final ActorRef writeResult = system.actorOf(Props.create(WriteResult.class));

        final EventData event = new EventDataBuilder("my-event")
                .eventId(UUID.randomUUID())
                .data("my event data")
                .metadata("my first event")
                .build();

        final WriteEvents writeEvents = new WriteEventsBuilder("my-stream")
                .addEvent(event)
                .expectAnyVersion()
                .build();

        connection.tell(writeEvents, writeResult);

    }


    public static class WriteResult extends AbstractActor {

//        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(WriteEventsCompleted.class, m -> {
//                        log.info("range: {}, position: {}", m.numbersRange(), m.position());
                        context().system().terminate();
                    })
                    .match(Status.Failure.class, f -> {
//                        final EsException exception = (EsException) f.cause();
                        //log.error(exception, exception.toString());
                    })
                    .build();
        }
    }
}
