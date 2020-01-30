<?php
declare(strict_types=1);

namespace App\Domain;

final class ReservationConfirmed extends Event
{
    public int $roomNumber;
    public \DateTimeImmutable $startDate;
    public \DateTimeImmutable $endDate;

    public function __construct(int $roomNumber, \DateTimeImmutable $startDate, \DateTimeImmutable $endDate)
    {
        $this->roomNumber = $roomNumber;
        $this->startDate = $startDate;
        $this->endDate = $endDate;
    }
}
