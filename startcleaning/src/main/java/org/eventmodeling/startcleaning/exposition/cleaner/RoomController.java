package org.eventmodeling.startcleaning.exposition.cleaner;


import org.eventmodeling.startcleaning.domain.CleaningPlanning;
import org.eventmodeling.startcleaning.infrastructure.InMemoryEventStore;
import org.eventmodeling.startcleaning.usecase.StartCleaningHandler;
import org.eventmodeling.startcleaning.usecase.StartRoomCleaning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {

    private final StartCleaningHandler startCleaningHandler;

    public RoomController() {
        this.startCleaningHandler = new StartCleaningHandler(new InMemoryEventStore());
    }

    @PostMapping("/xtra-cleaning/room/{roomId}/start")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity greeting(@PathVariable(value = "roomId") String roomId) {
        startCleaningHandler.handle(StartRoomCleaning.from(roomId));
        return ResponseEntity.ok().build();
    }

}
