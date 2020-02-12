<?php
declare(strict_types=1);

namespace App;

trait EventsAware
{
    public function match($event, array $eventTypes, callable $default = null)
    {
        foreach ($eventTypes as $type => $fn) {
            if ($event instanceof $type) {
                return $fn();
            }
        }
        if ($default) {
            return $default();
        }
        throw new \TypeError();
    }
}
