<?php
declare(strict_types=1);

namespace App\Domain;

final class RoomAvailability
{
    private int $roomNumber;
    private bool $isAvailable;

    public function __construct(int $roomNumber, bool $isAvailable = true)
    {
        $this->roomNumber = $roomNumber;
        $this->isAvailable = $isAvailable;
    }

    public function isAvailable(): bool
    {
        return $this->isAvailable;
    }

    public function unavailable()
    {
        $this->isAvailable = false;
    }

    public function available()
    {
        $this->isAvailable = true;
    }
}
