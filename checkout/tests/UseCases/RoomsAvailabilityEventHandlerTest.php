<?php
declare(strict_types=1);

namespace App\Tests\UseCases;

use App\Domain\CheckedInEvent;
use App\Domain\CheckedOutEvent;
use App\Domain\Events;
use App\Domain\ReservationConfirmed;
use App\Domain\RoomAvailability;
use App\Domain\RoomsAvailability;
use App\Infra\InMemoryRepository;
use App\UseCases\RoomsAvailabilityEventHandler;
use PHPUnit\Framework\TestCase;

class RoomsAvailabilityEventHandlerTest extends TestCase
{
    /**
     * @test
     */
    public function no_events(): void
    {
        // given
        $events = new Events([]);
        $repository = new InMemoryRepository($events);
        $tested = new RoomsAvailabilityEventHandler($repository);

        // when
        $roomsAvailability = $tested->getView(\DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-18'));

        // then
        /** @var RoomAvailability $roomAvailability */
        foreach ($roomsAvailability as $roomAvailability) {
            self::assertTrue($roomAvailability->isIsAvailable());
        }
    }

    /**
     * @test
     */
    public function one_room_is_unavailable(): void
    {
        // given
        $events = new Events([
            new CheckedInEvent(3)
        ]);
        $repository = new InMemoryRepository($events);
        $tested = new RoomsAvailabilityEventHandler($repository);

        // when
        $roomsAvailability = $tested->getView(\DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-18'));

        // then
        $expectedRoomsAvailability = new RoomsAvailability([
            1 => new RoomAvailability(1),
            2 => new RoomAvailability(2),
            3 => new RoomAvailability(3, false),
        ]);
        self::assertEquals(
            $expectedRoomsAvailability,
            $roomsAvailability
        );
    }

    /**
     * @test
     */
    public function room_is_available_again(): void
    {
        // given
        $events = new Events([
            new CheckedInEvent(3),
            new CheckedInEvent(1),
            new CheckedInEvent(2),
            new CheckedOutEvent(1, \DateTimeImmutable::createFromFormat('Y-m-d', '2020-01-18')),
        ]);
        $repository = new InMemoryRepository($events);
        $tested = new RoomsAvailabilityEventHandler($repository);

        // when
        $roomsAvailability = $tested->getView(\DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-18'));

        // then
        $expectedRoomsAvailability = new RoomsAvailability([
            1 => new RoomAvailability(1),
            2 => new RoomAvailability(2, false),
            3 => new RoomAvailability(3, false),
        ]);
        self::assertEquals(
            $expectedRoomsAvailability,
            $roomsAvailability
        );
    }

    /**
     * @test
     */
    public function room_availability_when_reservation_confirmed(): void
    {
        // given
        $events = new Events([
            new ReservationConfirmed(
                3,
                \DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-10'),
                \DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-18')
            ), new ReservationConfirmed(
                1,
                \DateTimeImmutable::createFromFormat('Y-m-d', '2020-04-22'),
                \DateTimeImmutable::createFromFormat('Y-m-d', '2020-04-30')
            ),
        ]);
        $repository = new InMemoryRepository($events);
        $tested = new RoomsAvailabilityEventHandler($repository);

        // when
        $roomsAvailability = $tested->getView(\DateTimeImmutable::createFromFormat('Y-m-d', '2020-02-15'));

        // then
        $expectedRoomsAvailability = new RoomsAvailability([
            1 => new RoomAvailability(1),
            2 => new RoomAvailability(2),
            3 => new RoomAvailability(3, false),
        ]);
        self::assertEquals(
            $expectedRoomsAvailability,
            $roomsAvailability
        );
    }
}
