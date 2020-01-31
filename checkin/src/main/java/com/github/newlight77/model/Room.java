package com.github.newlight77.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {
    private String roomNumber;
    private String type;
    private boolean available;

}
