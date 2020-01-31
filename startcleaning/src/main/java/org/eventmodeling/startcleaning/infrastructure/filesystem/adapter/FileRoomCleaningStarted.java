package org.eventmodeling.startcleaning.infrastructure.filesystem.adapter;

public class FileRoomCleaningStarted implements FileEvent {
    private int roomId;
    private String date;

    public FileRoomCleaningStarted() {
    }

    public FileRoomCleaningStarted(int roomId, String date) {
        this.roomId = roomId;
        this.date = date;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
