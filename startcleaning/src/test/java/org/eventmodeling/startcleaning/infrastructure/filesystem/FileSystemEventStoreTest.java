package org.eventmodeling.startcleaning.infrastructure.filesystem;

import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.RoomCleaningStartedAdapter;
import org.junit.Test;

import java.time.LocalDate;

public class FileSystemEventStoreTest {

    @Test
    public void should_write_file_on_disk() {
        FileSystemEventStore fileSystemEventStore = new FileSystemEventStore("/tmp/events/cleaning/");
        fileSystemEventStore.adapter(RoomCleaningStarted.class, new RoomCleaningStartedAdapter());

        fileSystemEventStore.add(new RoomCleaningStarted(new Room(101), LocalDate.now()));
    }
}