<?php
declare(strict_types=1);

namespace App;

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
            fn($event) => $event->roomNumber === $roomNumber
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