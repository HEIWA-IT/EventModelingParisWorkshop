<?php
declare(strict_types=1);

namespace App\Infra;

use App\Domain\Events;
use App\Domain\Repository;
use App\Domain\Room;
use App\Domain\RoomException;

final class InMemoryRepository implements Repository
{
    private Events $events;

    public function __construct(Events $events)
    {
        $this->events = $events;
    }

    public function getRoomByRoomNumber(int $roomNumber): Room
    {
        $roomEvents = $this->events->filter(
            function ($event) use ($roomNumber) {
                return $event->roomNumber === $roomNumber;
            }
        );

        if ($roomEvents->isEmpty()) {
            throw new RoomException("Room [$roomNumber] was never checked in.");
        }

        return Room::fromEvents($roomEvents);
    }

    public function getAllEvents(): Events
    {
        return $this->events;
    }

    public function store(Events $raisedEvents)
    {
        foreach ($raisedEvents as $raisedEvent) {
            $this->events->add($raisedEvent);
        }
    }
}
