package toto;

public class StartRoomCleaning implements Command {
    private final int roomId;

    public StartRoomCleaning(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }
}
