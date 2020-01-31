<?php
declare(strict_types=1);

namespace App\Domain;

interface EventStore
{
    public function getAllEvents(): Events;

    public function store(Events $raisedEvents);

    public function getRoomByRoomNumber(int $roomNumber): Room;
}
