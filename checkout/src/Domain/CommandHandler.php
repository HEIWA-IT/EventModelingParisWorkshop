<?php
declare(strict_types=1);

namespace App\Domain;

use Doctrine\Common\Collections\Collection;

interface CommandHandler
{
    public function handle(Command $command): Collection;
}
