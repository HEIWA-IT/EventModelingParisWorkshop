package org.eventmodeling.startcleaning.infrastructure.filesystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.EventStore;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.EventAdapter;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.FileEvent;
import org.eventmodeling.startcleaning.usecase.EventHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileSystemEventStore implements EventStore {

    private final String pathname;
    private Map<Class, Set<EventHandler>> allHandlers = new HashMap<>();
    private Map<Class, EventAdapter> allAdapter = new HashMap<>();

    public FileSystemEventStore(String pathname) {
        this.pathname = pathname;
    }

    public <T> void register(Class<T> eventClass, EventHandler handler) {
        Set<EventHandler> eventHandlers = allHandlers.getOrDefault(eventClass, new HashSet<>());
        eventHandlers.add(handler);
        allHandlers.put(eventClass, eventHandlers);
    }

    public <T> void adapter(Class<T> eventClass, EventAdapter adapter) {
        allAdapter.put(eventClass, adapter);
    }

    @Override
    public Collection<Event> all() {
        return null;
    }

    @Override
    public void add(Event event) {
        try {
            String fileName = getFileName(event);
            FileEvent fileEvent = toFileEvent(event);
            String filePath = this.pathname + fileName;
            writeJsonToFile(filePath, fileEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonToFile(String filePath, FileEvent fileEvent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), fileEvent);
    }

    private FileEvent toFileEvent(Event event) {
        EventAdapter eventAdapter = allAdapter.get(event.getClass());
        return eventAdapter.adapt(event);
    }

    private String getFileName(Event event) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss-SSS");
        String text = LocalDateTime.now().format(formatters);
        return text + "_" + event.getClass().getSimpleName() + ".json";
    }

    @Override
    public <T> Set<T> getEventsOfType(Class<T> type) {
        return null;
    }
}
