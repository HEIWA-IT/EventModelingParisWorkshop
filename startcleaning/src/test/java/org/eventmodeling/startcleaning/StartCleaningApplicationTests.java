package org.eventmodeling.startcleaning;

import org.eventmodeling.startcleaning.domain.Room;
import org.eventmodeling.startcleaning.domain.RoomCleaningStarted;
import org.eventmodeling.startcleaning.infrastructure.RealEventStore;
import org.eventmodeling.startcleaning.infrastructure.filesystem.adapter.RoomCleaningStartedAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class StartCleaningApplicationTests {

	@Test
	void contextLoads() {
		RealEventStore realEventStore = new RealEventStore();
		realEventStore.adapter(RoomCleaningStarted.class, new RoomCleaningStartedAdapter());
		realEventStore.add(new RoomCleaningStarted(new Room(301), LocalDate.now()));
	}

}
