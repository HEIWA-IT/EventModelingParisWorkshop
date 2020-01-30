<?php
declare(strict_types=1);

namespace App\Domain;

final class CheckedOutEvent extends Event
{
    public int $roomNumber;
    public \DateTimeImmutable $checkOutDate;

    public function __construct(int $roomNumber, \DateTimeImmutable $checkOutDate)
    {
        $this->roomNumber = $roomNumber;
        $this->checkOutDate = $checkOutDate;
    }
}
