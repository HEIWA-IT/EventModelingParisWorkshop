using CleaningRequestConfirmed.Logic;
using Infrastructure;
using Microsoft.AspNetCore.Mvc;

namespace CleaningRequestConfirmed
{
    public class EventBusController : Controller
    {
        private readonly IEventBus _eventBus;

        public EventBusController(IEventBus eventBus)
        {
            _eventBus = eventBus;
        }

        [HttpPost]
        public void CleaningRequested([FromBody]CleaningRequested evt)
        {
            _eventBus.Publish(evt);
        }
    }
}