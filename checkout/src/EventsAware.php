<?php
declare(strict_types=1);

namespace App;

trait EventsAware
{
    public function match($var, array $map, callable $default = null)
    {
        foreach ($map as $type => $fn) {
            if ($var instanceof $type) {
                return $fn();
            }
        }
        if ($default) {
            return $default();
        }
        throw new \TypeError();
    }
}
