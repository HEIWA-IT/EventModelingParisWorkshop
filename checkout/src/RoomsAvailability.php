<?php
declare(strict_types=1);

namespace App;

use Doctrine\Common\Collections\ArrayCollection;

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

    public function makeUnavailable(int $roomNumber)
    {
        $this->get($roomNumber)->makeUnavailable();
    }

    public function makeAvailable(int $roomNumber)
    {
        $this->get($roomNumber)->makeAvailable();
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
