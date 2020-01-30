<?php
declare(strict_types=1);

namespace App\Domain;

final class Room
{
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

            if ($roomEvent instanceof CheckedInEvent) {
                $room->occupied = true;
            }
            if ($roomEvent instanceof CheckedOutEvent) {
                $room->occupied = false;
            }
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
