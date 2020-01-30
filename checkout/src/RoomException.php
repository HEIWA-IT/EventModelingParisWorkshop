<?php
declare(strict_types=1);

namespace App;

final class RoomException extends \Exception
{
    public function __construct(string $string)
    {
        parent::__construct($string);
    }
}
