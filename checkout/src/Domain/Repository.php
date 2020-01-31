<?php
declare(strict_types=1);

namespace App\Domain;

interface Repository
{
    public function store(Events $raisedEvents);

    public function getAllEvents(): Events;

    public function getRoomByRoomNumber(int $roomNumber): Room;
}
