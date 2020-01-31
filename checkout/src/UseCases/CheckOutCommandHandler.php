<?php
declare(strict_types=1);

namespace App\UseCases;

use App\Domain\Events;
use App\Domain\EventStore;

final class CheckOutCommandHandler implements CommandHandler
{
    private EventStore $eventStore;

    public function __construct(EventStore $eventStore)
    {
        $this->eventStore = $eventStore;
    }

    public function handle(Command $command): Events
    {
        $room = $this->eventStore->getRoomByRoomNumber($command->roomNumber);

        $room->checkOut($command->checkOutDate);

        $this->eventStore->store($room->getRaisedEvents());

        return $room->getRaisedEvents();
    }
}
