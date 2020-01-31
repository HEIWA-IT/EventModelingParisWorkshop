<?php
declare(strict_types=1);

namespace App\Domain;

final class CheckedInEvent implements Event
{
    use EventFunctions;

    public int $roomNumber;

    public function __construct(int $roomNumber)
    {
        $this->roomNumber = $roomNumber;
    }

    public static function fromJson($jsonData): self
    {
        $json_decode = \json_decode($jsonData, true);

        return new self($json_decode['roomNumber']);
    }

    public function toArray(): array
    {
        return [
            'roomNumber' => $this->roomNumber,
        ];
    }
}
