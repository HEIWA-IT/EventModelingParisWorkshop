<?php
declare(strict_types=1);

namespace App\Tests\Infra;

use App\Domain\CheckedInEvent;
use App\Domain\Events;
use App\Domain\Room;
use App\Domain\RoomException;
use App\Infra\InMemoryRepository;
use PHPUnit\Framework\TestCase;

class InMemoryRepositoryTest extends TestCase
{
    /**
     * @test
     */
    public function no_events_in_store()
    {
        // then
        $this->expectException(RoomException::class);

        // given
        $tested = new InMemoryRepository(new Events());

        // when
        $tested->getRoomByRoomNumber(304);
    }

    /**
     * @test
     */
    public function no_events_for_room_number()
    {
        // then
        $this->expectException(RoomException::class);

        // given
        $tested = new InMemoryRepository(new Events([
            new CheckedInEvent(404)
        ]));

        // when
        $tested->getRoomByRoomNumber(304);
    }

    /**
     * @test
     */
    public function events_for_room_number_in_store()
    {
        // given
        $events = new Events([
            new CheckedInEvent(304)
        ]);
        $expectedRoom = Room::fromEvents($events);

        $tested = new InMemoryRepository($events);

        // when
        $room = $tested->getRoomByRoomNumber(304);

        // then
        self::assertEquals($expectedRoom, $room);
    }
}
