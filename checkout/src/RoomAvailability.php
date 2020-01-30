<?php
declare(strict_types=1);

namespace App;

final class RoomAvailability
{
    private int $roomNumber;
    private bool $isAvailable;

    public function __construct(int $roomNumber, bool $isAvailable = true)
    {
        $this->roomNumber = $roomNumber;
        $this->isAvailable = $isAvailable;
    }

    public function isIsAvailable(): bool
    {
        return $this->isAvailable;
    }

    public function makeUnavailable()
    {
        $this->isAvailable = false;
    }

    public function makeAvailable()
    {
        $this->isAvailable = true;
    }
}
