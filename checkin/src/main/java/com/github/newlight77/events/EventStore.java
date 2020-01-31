package com.github.newlight77.events;

import com.github.newlight77.repository.RoomPublisher;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EventStore {

    private String pathname = "./tmp/events/checkin/";
    private List<EventListener> listeners = new ArrayList<>();

    public void register(EventListener listener) {
        listeners.add(listener);
    }

    private RoomPublisher publisher;

    public EventStore(RoomPublisher publisher) {
        this.publisher = publisher;
    }


    public void eventFired(JSONObject json) {
        this.storeEvent(json);
        this.notifyListeners(json);
    }

    private void storeEvent(JSONObject json) {
        FileWriter writer = null;
        try {
            publisher.publish(json);
            new File(pathname).mkdirs();
            writer = new FileWriter("./tmp/events/checkin/rooms.json");
            writer.write(json.toJSONString());
            writer.close();
        } catch (IOException e) {
        }
    }

    public String readJson(String roomNumber) {
        FileReader reader = null;
        try {
            String json = Files.readString(Paths.get(pathname + "rooms-" + roomNumber + ".json"));
            return new JSONParser().parse(json).toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "error reading from file";
    }

    private void notifyListeners(JSONObject event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
