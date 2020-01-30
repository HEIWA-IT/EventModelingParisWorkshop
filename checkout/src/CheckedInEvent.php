<?php
declare(strict_types=1);

namespace App;

final class CheckedInEvent implements Event
{
    public int $roomNumber;

    public function __construct(int $roomNumber)
    {
        $this->roomNumber = $roomNumber;
    }
}
