<?php
declare(strict_types=1);

namespace App\Tests;

use App\Command;
use App\CommandHandler;
use App\Events;
use App\InMemoryRepository;
use App\Repository;
use PHPUnit\Framework\TestCase;

abstract class Specification extends TestCase
{
    protected Events $events;
    protected Repository $repository;
    protected CommandHandler $handler;
    protected Events $produced;

    abstract function Given(): Events;

    abstract function When(): Command;

    abstract function OnHandler(Command $command): CommandHandler;

    public function setUp(): void
    {
        $this->events = $this->Given();
        $this->repository = new InMemoryRepository($this->events);
        $this->handler = $this->OnHandler($this->When());
        $this->handler->handle($this->When());
        $this->produced = $this->repository->getAllEvents();
    }

    public function PrintDocumentation(Specification $specification)
    {
        echo "Given: ";
        foreach ($specification->Given() as $event) {
            echo $event . \PHP_EOL;
        }
        echo "When: " . $specification->When();
        echo "Expect: ";
        foreach ($this->produced as $producedEvent) {
            echo $producedEvent . \PHP_EOL;
        }
    }
}
