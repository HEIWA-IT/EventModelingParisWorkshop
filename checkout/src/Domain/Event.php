<?php
declare(strict_types=1);

namespace App\Domain;

abstract class Event
{
    public function __toString(): string
    {
        $explode = \explode('\\', \get_class($this));
        return \end($explode);
    }
}
