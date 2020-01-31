package com.github.newlight77.uc.checkin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomCheckinCompleted {
    private String customerName;
    private String checkinTime;
    private String roomNumber;
    private String badgeNumber;
    private String reservationNumber;
}
