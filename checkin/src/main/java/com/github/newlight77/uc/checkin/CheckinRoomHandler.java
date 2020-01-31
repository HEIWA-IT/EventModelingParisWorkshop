package com.github.newlight77.uc.checkin;

import com.github.newlight77.events.EventStore;
import org.json.simple.JSONObject;

public class CheckinRoomHandler {

    private EventStore eventStore;

    public CheckinRoomHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void checkin(CheckinCommand command) {
        RoomCheckinCompleted event = RoomCheckinCompleted.builder()
                .customerName(command.getCustomerName())
                .checkinTime(command.getCheckinTime().toString())
                .roomNumber(command.getRoomNumber())
                .badgeNumber(command.getBadgeNumber())
                .reservationNumber(command.getReservationNumber())
                .build();

        JSONObject json = new JSONObject();
        json.put("customerName", event.getCustomerName());
        json.put("badgeNumber", event.getBadgeNumber());
        json.put("checkinTime", event.getCheckinTime());
        json.put("roomNumber", event.getRoomNumber());
        json.put("reservationNumber", event.getReservationNumber());

        eventStore.eventFired(json);
    }
}
