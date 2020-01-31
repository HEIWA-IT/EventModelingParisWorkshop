<?php
declare(strict_types=1);

namespace App\Tests\Infra;

use App\Domain\CheckedInEvent;
use App\Domain\Events;
use App\Infra\EventStoreRepository;
use EventStore\EventStore;
use EventStore\Http\GuzzleHttpClient;
use EventStore\StreamDeletion;
use GuzzleHttp\Client;
use PHPUnit\Framework\TestCase;

class EventStoreRepositoryTest extends TestCase
{
    private EventStore $eventStore;
    private string $streamName;

    protected function setUp(): void
    {
        $this->eventStore = new EventStore(
            'http://127.0.0.1:2113',
            new GuzzleHttpClient(new Client(['auth' => ['admin', 'changeit']]))
        );
        $this->streamName = 'stream_checkout_test';
    }

    /**
     * @test
     */
    public function Store()
    {
        // given
        $eventStoreRepository = new EventStoreRepository(
            $this->eventStore,
            $this->streamName
        );
        $eventsToStore = new Events([
            new CheckedInEvent(304),
        ]);

        // when
        $eventStoreRepository->store($eventsToStore);

        // then
        self::assertEquals(
            $eventsToStore,
            $eventStoreRepository->getAllEvents()
        );
    }

    public function tearDown(): void
    {
        $this->eventStore->deleteStream($this->streamName, StreamDeletion::SOFT());
    }
}
