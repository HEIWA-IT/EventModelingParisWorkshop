<?php
declare(strict_types=1);

namespace App\UseCases;

use App\Domain\Events;

interface CommandHandler
{
    public function handle(Command $command): Events;
}
