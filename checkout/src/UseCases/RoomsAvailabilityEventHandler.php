<?php
declare(strict_types=1);

namespace App\UseCases;

use App\Domain\CheckedInEvent;
use App\Domain\CheckedOutEvent;
use App\Domain\Repository;
use App\Domain\ReservationConfirmed;
use App\Domain\RoomsAvailability;
use App\EventsAware;
use League\Period\Period;

final class RoomsAvailabilityEventHandler
{
    use EventsAware;

    private Repository $repository;

    public function __construct(Repository $repository)
    {
        $this->repository = $repository;
    }

    public function getView(\DateTimeImmutable $atDate): RoomsAvailability
    {
        $roomsAvailability = RoomsAvailability::generate(3);

        foreach ($this->repository->getAllEvents() as $event) {
            $this->match($event, [
                CheckedInEvent::class => fn() => $roomsAvailability->unavailable($event->roomNumber),
                CheckedOutEvent::class => fn() => $roomsAvailability->available($event->roomNumber),
                ReservationConfirmed::class => fn() => $roomsAvailability->unavailable(
                    $event->roomNumber,
                    $atDate,
                    new Period($event->startDate, $event->endDate)
                ),
            ]);
        }

        return $roomsAvailability;
    }
}
