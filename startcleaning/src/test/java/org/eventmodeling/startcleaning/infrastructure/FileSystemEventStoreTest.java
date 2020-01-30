package org.eventmodeling.startcleaning.infrastructure;

import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class FileSystemEventStoreTest {

    @Test
    public void should_write_file_on_disk() {
        FileSystemEventStore fileSystemEventStore = new FileSystemEventStore();
        fileSystemEventStore.add(new RoomCleaningStarted(new Room(101), LocalDate.now()));


    }
}