<?php
declare(strict_types=1);

namespace App;

abstract class Command
{
    public function __toString(): string
    {
        return __CLASS__;
    }
}
