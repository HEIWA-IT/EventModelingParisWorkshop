<?php
declare(strict_types=1);

namespace App\Infra;

use App\Domain\CheckedInEvent;
use App\Domain\Events;
use App\Domain\Repository;
use App\Domain\Room;
use EventStore\EventStore;
use EventStore\StreamFeed\EntryEmbedMode;
use EventStore\WritableEvent;
use EventStore\WritableEventCollection;

final class EventStoreRepository implements Repository
{
    private string $streamName;
    private EventStore $eventStore;

    public function __construct(EventStore $eventStore, string $streamName)
    {
        $this->streamName = $streamName;
        $this->eventStore = $eventStore;
    }

    public function store(Events $raisedEvents)
    {
        $writableEvents = [];

        foreach ($raisedEvents as $raisedEvent) {
            $writableEvents[] = WritableEvent::newInstance((string)$raisedEvent, $raisedEvent->toArray());
        }

        $this->eventStore->writeToStream($this->streamName, new WritableEventCollection($writableEvents));
    }

    public function getAllEvents(): Events
    {
        $streamFeed = $this->eventStore->openStreamFeed($this->streamName, EntryEmbedMode::BODY());

        $json = $streamFeed->getJson();

        $events = new Events();

        foreach ($json['entries'] as $entry) {
            if ($entry['eventType'] === CheckedInEvent::eventType()) {
                $events->add(
                    CheckedInEvent::fromJson($entry['data'])
                );
            }
        }

        return $events;
    }

    public function getRoomByRoomNumber(int $roomNumber): Room
    {
    }
}
