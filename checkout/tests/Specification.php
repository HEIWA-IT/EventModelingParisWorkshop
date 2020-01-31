<?php
declare(strict_types=1);

namespace App\Tests;

use App\Domain\Events;
use App\Domain\Repository;
use App\Infra\InMemoryRepository;
use App\UseCases\Command;
use App\UseCases\CommandHandler;
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
        echo "Given: " . \PHP_EOL;
        foreach ($specification->Given() as $event) {
            echo "\t" . $event . \PHP_EOL;
        }
        echo "When: " . $specification->When() . \PHP_EOL;
        echo "Expect: " . \PHP_EOL;
        foreach ($this->produced as $producedEvent) {
            echo "\t" . $producedEvent . \PHP_EOL;
        }
    }

    public function tearDown(): void
    {
        $this->PrintDocumentation($this);
    }
}
