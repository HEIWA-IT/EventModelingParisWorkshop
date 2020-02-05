<?php
declare(strict_types=1);

namespace App\Domain;

use Doctrine\Common\Collections\ArrayCollection;
use League\Period\Period;

final class RoomsAvailability extends ArrayCollection
{
    public static function generate(int $numberOfRooms): RoomsAvailability
    {
        $roomsAvailability = new RoomsAvailability();

        for ($i = 1; $i <= $numberOfRooms; $i++) {
            $roomsAvailability->set($i, new RoomAvailability($i));
        }

        return $roomsAvailability;
    }

    public function unavailable(int $roomNumber, \DateTimeInterface $atDate = null, Period $period = null)
    {
        if (null === $period || $period->contains($atDate)) {
            $this->get($roomNumber)->unavailable();
        }
    }

    public function available(int $roomNumber)
    {
        $this->get($roomNumber)->available();
    }

    public function current(): RoomAvailability
    {
        return parent::current();
    }

    public function get($key): RoomAvailability
    {
        return parent::get($key);
    }
}
