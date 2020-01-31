<?php
declare(strict_types=1);

namespace App\UseCases;

abstract class Command
{
    public function __toString(): string
    {
        $explode = \explode('\\', \get_class($this));
        return \end($explode);
    }
}