<?php
declare(strict_types=1);

namespace App\Domain;

final class CheckedInEvent extends Event
{
    public int $roomNumber;

    public function __construct(int $roomNumber)
    {
        $this->roomNumber = $roomNumber;
    }
}
