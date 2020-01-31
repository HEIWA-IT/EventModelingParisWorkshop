<?php
declare(strict_types=1);

namespace App\Domain;

final class CheckedOutEvent implements Event
{
    use EventFunctions;

    public int $roomNumber;
    public \DateTimeImmutable $checkOutDate;

    public function __construct(int $roomNumber, \DateTimeImmutable $checkOutDate)
    {
        $this->roomNumber = $roomNumber;
        $this->checkOutDate = $checkOutDate;
    }

    public static function fromJson($jsonData): self
    {
        $json_decode = \json_decode($jsonData, true);

        return new self(
            $json_decode['roomNumber'],
            $json_decode['checkOutDate']
        );
    }

    public function toArray(): array
    {
        return [
            'roomNumber' => $this->roomNumber,
            'checkOutDate' => $this->checkOutDate,
        ];
    }
}
