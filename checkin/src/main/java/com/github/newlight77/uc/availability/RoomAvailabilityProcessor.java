package com.github.newlight77.uc.availability;

import com.github.newlight77.events.EventListener;
import com.github.newlight77.events.EventStore;
import com.github.newlight77.model.Room;
import com.github.newlight77.repository.RoomReadRepository;
import com.github.newlight77.repository.RoomSubscriber;
import com.github.newlight77.repository.RoomWriteRepository;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoomAvailabilityProcessor implements EventListener {

    private RoomWriteRepository roomWriteRepository;
    private RoomReadRepository roomReadRepository;
    private RoomSubscriber roomSubscriber;

    public RoomAvailabilityProcessor(RoomReadRepository roomReadRepository, RoomWriteRepository roomWriteRepository, RoomSubscriber roomSubscriber, EventStore eventStore) {
        this.roomReadRepository = roomReadRepository;
        this.roomWriteRepository = roomWriteRepository;
        this.roomSubscriber = roomSubscriber;
        eventStore.register(this);
    }

    public List<Room> availableRooms() {
        List<Room> rooms = new ArrayList<>();
        roomSubscriber.subscribe(rooms::add);
        return roomReadRepository.getAvailableRooms();
    }

    @Override
    public void onEvent(JSONObject event) {
        if (event.containsKey("checkinTime")) {
            this.roomWriteRepository.updateRoomAvailability(event.get("roomNumber").toString(), false);
        }
    }
}
