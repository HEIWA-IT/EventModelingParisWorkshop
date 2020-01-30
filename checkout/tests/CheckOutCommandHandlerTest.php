<?php
declare(strict_types=1);

namespace App\Tests;

use App\CheckedInEvent;
use App\CheckOutCommand;
use App\CheckOutCommandHandler;
use App\Command;
use App\CommandHandler;
use App\Events;

class CheckOutCommandHandlerTest extends Specification
{
    function Given(): Events
    {
        return new Events([
            new CheckedInEvent(304)
        ]);
    }

    function When(): Command
    {
        return new CheckOutCommand(
            304,
            \DateTimeImmutable::createFromFormat('Y-m-d', '2020-01-29')
        );
    }

    function OnHandler(Command $command): CommandHandler
    {
        // TODO should get the handler from a map, indexed by commands
        return new CheckOutCommandHandler($this->repository);
    }

    /**
     * @test
     */
    public function check_out_when_check_in_happened(): void
    {
        // then
        self::assertCount(2, $this->produced);
        self::assertEquals(304, $this->produced->get(1)->roomNumber);
    }
}
