package com.github.newlight77.uc.checkin;

import com.github.newlight77.events.EventListener;
import com.github.newlight77.events.EventStore;
import com.github.newlight77.repository.RoomPublisher;
import com.github.newlight77.repository.database.HotelDatabase;
import com.github.newlight77.specification.Beha4j;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomCheckinTest {
    //    @Test
    //    public void should_process_room_checkin_event() {
    public static void main(String[] args) {

        final HotelDatabase database = new HotelDatabase();
        final RoomPublisher roomPublisher = new RoomPublisher();
        final EventStore eventStore = new EventStore(roomPublisher);
        final CheckinCommand.CheckinCommandBuilder builder = CheckinCommand.builder();
        final CheckinRoomHandler handler = new CheckinRoomHandler(eventStore);
        final TestEventListener listener = new TestEventListener();

        Beha4j
            .scenario("should_process_room_checkin_event")
            .given("a customer named Jane Jackson", name -> {
                builder
                        .customerName("Jane Jackson")
                        .checkinTime(LocalDateTime.of(2020, 1, 30, 10, 11, 21))
                        .badgeNumber("12345")
                        .reservationNumber("1234556")
                        .roomNumber("12312");
                eventStore.register(listener);
            })
            .when("Jane Jackson books a room in our hotel", name -> {
                handler.checkin(builder.build());
            })
            .then("a booking event is created", name -> {
                assertThat(listener.eventThrown).isNotNull();
                String event = listener.eventThrown.toJSONString();
                assertThat(event).contains("\"checkinTime\":\"2020-01-30T10:11:21\"");
                assertThat(event).contains("\"roomNumber\":\"12312\"");
                assertThat(event).contains("\"reservationNumber\":\"1234556\"");
                assertThat(event).contains("\"badgeNumber\":\"12345\"");
                assertThat(event).contains("\"customerName\":\"Jane Jackson\"");
            })
        .print();
    }

    private static class TestEventListener implements EventListener {
        JSONObject eventThrown;
        @Override
        public void onEvent(JSONObject event) {
            eventThrown = event;
        }
    }

}
