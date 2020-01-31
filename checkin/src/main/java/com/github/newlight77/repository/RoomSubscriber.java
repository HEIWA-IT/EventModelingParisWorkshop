package com.github.newlight77.repository;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.github.newlight77.model.Room;
import eventstore.akka.Settings;
import eventstore.akka.SubscriptionObserver;
import eventstore.akka.tcp.ConnectionActor;
import eventstore.core.*;
import eventstore.j.EsConnection;
import eventstore.j.EsConnectionFactory;
import eventstore.j.ReadEventBuilder;
import eventstore.j.SettingsBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.function.Consumer;

public class RoomSubscriber {

    public void read() {
        final ActorSystem system = ActorSystem.create();
        final Settings settings = new SettingsBuilder()
                .address(new InetSocketAddress("127.0.0.1", 1113))
                .defaultCredentials("admin", "changeit")
                .build();
        final ActorRef connection = system.actorOf(ConnectionActor.getProps(settings));
        final ActorRef readResult = system.actorOf(Props.create(ReadResult.class));

        final ReadEvent readEvent = new ReadEventBuilder("rooms")
                .first()
                .resolveLinkTos(false)
                .requireMaster(true)
                .build();

        connection.tell(readEvent, readResult);
    }

    public static class ReadResult extends AbstractActor {
        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        @Override
        public AbstractActor.Receive createReceive() {
            return receiveBuilder()
                    .match(ReadEventCompleted.class, m -> {
                        final Event event = m.event();
                        log.info("event: {}", event);
                        context().system().terminate();
                    })
                    .match(Status.Failure.class, f -> {
                        final EsException exception = (EsException) f.cause();
                        log.error(exception, exception.toString());
                        context().system().terminate();
                    })
                    .build();
        }
    }

    public void subscribe(Consumer<Room> callback) {
        final ActorSystem system = ActorSystem.create();
        final EsConnection connection = EsConnectionFactory.create(system);
        final Closeable closeable = connection.subscribeToAll(new SubscriptionObserver<IndexedEvent>() {
            @Override
            public void onLiveProcessingStart(Closeable subscription) {
                system.log().info("live processing started");
            }

            @Override
            public void onEvent(IndexedEvent indexedEvent, Closeable subscription) {
                String json = new String(indexedEvent.event().data().data().value().toArray());
                system.log().info(json);
                try {
                    Object object = new JSONParser().parse(json);
                    system.log().info(object.toString());
                    callback.accept((Room) object); // WIP
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                system.log().error(e.toString());
            }

            @Override
            public void onClose() {
                system.log().error("subscription closed");
            }
        }, false, null);
    }
}
