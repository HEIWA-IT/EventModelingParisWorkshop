<?php
declare(strict_types=1);

namespace App\UseCases;

use App\Domain\Events;
use App\Domain\Repository;

final class CheckOutCommandHandler implements CommandHandler
{
    private Repository $repository;

    public function __construct(Repository $repository)
    {
        $this->repository = $repository;
    }

    public function handle(Command $command): Events
    {
        $room = $this->repository->getRoomByRoomNumber($command->roomNumber);

        $room->checkOut($command->checkOutDate);

        $this->repository->store($room->getRaisedEvents());

        return $room->getRaisedEvents();
    }
}
