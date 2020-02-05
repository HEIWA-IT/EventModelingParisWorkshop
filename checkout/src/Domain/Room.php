<?php
declare(strict_types=1);

namespace App\Domain;

use App\EventsAware;

final class Room
{
    use EventsAware;

    private int $roomNumber;
    private bool $occupied;
    private Events $raisedEvents;

    public function __construct()
    {
        $this->raisedEvents = new Events();
    }

    public static function fromEvents(iterable $roomEvents): Room
    {
        $room = new Room();

        foreach ($roomEvents as $roomEvent) {
            $room->roomNumber = $roomEvent->roomNumber;

            $room->occupied = $room->match($roomEvent, [
                CheckedInEvent::class => fn() => true,
                CheckedOutEvent::class => fn() => false,
            ]);
        }

        return $room;
    }

    public function checkOut(\DateTimeImmutable $checkOutDate)
    {
        if (!$this->occupied) {
            throw new RoomException("Room [{$this->roomNumber}] cannot be checked out if it is not occupied.");
        }

        $this->raisedEvents->add(
            new CheckedOutEvent($this->roomNumber, $checkOutDate)
        );
    }

    public function getRaisedEvents(): Events
    {
        return $this->raisedEvents;
    }
}
