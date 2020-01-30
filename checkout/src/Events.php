<?php
declare(strict_types=1);

namespace App;

use Doctrine\Common\Collections\ArrayCollection;

final class Events extends ArrayCollection
{
    public function current(): Event
    {
        return parent::current();
    }
}
