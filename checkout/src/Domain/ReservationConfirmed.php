<?php
declare(strict_types=1);

namespace App\Domain;

final class ReservationConfirmed implements Event
{
    use EventFunctions;

    public int $roomNumber;
    public \DateTimeImmutable $startDate;
    public \DateTimeImmutable $endDate;

    public function __construct(int $roomNumber, \DateTimeImmutable $startDate, \DateTimeImmutable $endDate)
    {
        $this->roomNumber = $roomNumber;
        $this->startDate = $startDate;
        $this->endDate = $endDate;
    }

    public static function fromJson($jsonData): self
    {
        $json_decode = \json_decode($jsonData, true);

        return new self(
            $json_decode['roomNumber'],
            $json_decode['startDate'],
            $json_decode['endDate']
        );
    }

    public function toArray(): array
    {
        return [
            'roomNumber' => $this->roomNumber,
            'startDate' => (string)$this->startDate,
            'endDate' => (string)$this->endDate,
        ];
    }
}
