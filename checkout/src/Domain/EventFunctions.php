<?php
declare(strict_types=1);

namespace App\Domain;

trait EventFunctions
{
    public function __toString(): string
    {
        return static::eventType();
    }

    public static function eventType(): string
    {
        $explode = \explode('\\', __CLASS__);
        return \end($explode);
    }

    abstract public static function fromJson($jsonData): self;

    abstract public function toArray(): array;
}
