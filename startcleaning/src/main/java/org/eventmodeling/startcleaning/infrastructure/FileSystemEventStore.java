package org.eventmodeling.startcleaning.infrastructure;

import org.eventmodeling.startcleaning.domain.Event;
import org.eventmodeling.startcleaning.domain.EventStore;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class FileSystemEventStore implements EventStore {
    @Override
    public Collection<Event> all() {
        return null;
    }

    @Override
    public void add(Event event) {
        String fileName = LocalDateTime.now() + event.getClass().getSimpleName();
            String pathname = ".//tmp//events//cleaning/" + fileName;

        try {
            File eventHome = new File("/tmp/events/cleaning/");
            File file = new File(eventHome, fileName);

//Create the file
            if (file.createNewFile())
            {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            FileOutputStream f = new FileOutputStream(new File(pathname));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(event);

            o.close();
            f.close();

            FileInputStream fi = new FileInputStream(new File(pathname));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Event pr1 = (Event) oi.readObject();
            System.out.println(pr1.toString());

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {

            System.out.println("Error initializing stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public <T> Set<T> getEventsOfType(Class<T> type) {
        return null;
    }
}
