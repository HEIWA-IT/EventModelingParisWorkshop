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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity greeting(@PathVariable(value = "roomId") String roomId) {
        return ResponseEntity.ok().build();
    }

}
