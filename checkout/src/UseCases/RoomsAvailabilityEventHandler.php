<?php
declare(strict_types=1);

namespace App\UseCases;

use App\Domain\CheckedInEvent;
use App\Domain\CheckedOutEvent;
use App\Domain\Repository;
use App\Domain\ReservationConfirmed;
use App\Domain\RoomsAvailability;

final class RoomsAvailabilityEventHandler
{
    private Repository $repository;

    public function __construct(Repository $repository)
    {
        $this->repository = $repository;
    }

    public function getView(\DateTimeImmutable $atDate): RoomsAvailability
    {
        $roomsAvailability = RoomsAvailability::generate(3);

        foreach ($this->repository->getAllEvents() as $event) {
            if ($event instanceof CheckedInEvent) {
                $roomsAvailability->makeUnavailable($event->roomNumber);
            }
            if ($event instanceof CheckedOutEvent) {
                $roomsAvailability->makeAvailable($event->roomNumber);
            }
            if ($event instanceof ReservationConfirmed) {
                if ($event->startDate <= $atDate && $atDate <= $event->endDate) {
                    $roomsAvailability->makeUnavailable($event->roomNumber);
                }
            }
        }

        return $roomsAvailability;
    }
}
