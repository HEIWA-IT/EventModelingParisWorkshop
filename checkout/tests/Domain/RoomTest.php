<?php
declare(strict_types=1);

namespace App\Tests\Domain;

use App\Domain\CheckedInEvent;
use App\Domain\CheckedOutEvent;
use App\Domain\Room;
use App\Domain\RoomException;
use PHPUnit\Framework\TestCase;

class RoomTest extends TestCase
{
    /**
     * @test
     */
    public function cannot_check_out_when_room_is_not_occupied()
    {
        // then
        $this->expectException(RoomException::class);

        // given
        $checkOutDate = \DateTimeImmutable::createFromFormat('Y-m-d', '2020-01-29');
        $tested = Room::fromEvents([
            new CheckedOutEvent(304, $checkOutDate)
        ]);

        // when
        $tested->checkOut($checkOutDate);
    }

    /**
     * @test
     */
    public function can_check_out_when_room_is_occupied()
    {
        // given
        $tested = Room::fromEvents([
            new CheckedInEvent(304)
        ]);
        $checkOutDate = \DateTimeImmutable::createFromFormat('Y-m-d', '2020-01-29');

        // when
        $tested->checkOut($checkOutDate);

        // then
        self::assertTrue(
            in_array(
                new CheckedOutEvent(304, $checkOutDate),
                $tested->getRaisedEvents()->toArray()
            )
        );
    }
}
