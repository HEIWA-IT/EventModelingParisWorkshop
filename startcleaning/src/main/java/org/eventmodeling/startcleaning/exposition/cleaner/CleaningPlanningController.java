package org.eventmodeling.startcleaning.exposition.cleaner;


import org.eventmodeling.startcleaning.infrastructure.InMemoryEventStore;
import org.eventmodeling.startcleaning.usecase.StartCleaningHandler;
import org.eventmodeling.startcleaning.usecase.StartRoomCleaning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CleaningPlanningController {

    public CleaningPlanningController() {

    }

    @GetMapping("/xtra-cleaning/room")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity roomsToClean() {
        String roomsToClean = "{\"rooms\": [\"301\", \"302\", \"303\"]  }";
        return ResponseEntity.ok().body(roomsToClean);
    }

}
