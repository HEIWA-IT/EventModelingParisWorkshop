package org.eventmodeling.startcleaning.exposition.cleaner;


import org.eventmodeling.startcleaning.usecase.StartCleaningHandler;
import org.eventmodeling.startcleaning.usecase.StartRoomCleaning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {

    private final StartCleaningHandler startCleaningHandler;

    public RoomController() {
        this.startCleaningHandler = null;
    }

    @PostMapping("/room/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity greeting(@RequestHeader(value = "id") String roomId) {
        startCleaningHandler.handle(StartRoomCleaning.from(roomId));
        return ResponseEntity.ok().build();
    }

}
