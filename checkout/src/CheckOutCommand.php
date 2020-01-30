<?php
declare(strict_types=1);

namespace App;

final class CheckOutCommand extends Command
{
    public int $roomNumber;
    public \DateTimeImmutable $checkOutDate;

    public function __construct(int $roomNumber, \DateTimeImmutable $checkOutDate)
    {
        $this->roomNumber = $roomNumber;
        $this->checkOutDate = $checkOutDate;
    }
}
