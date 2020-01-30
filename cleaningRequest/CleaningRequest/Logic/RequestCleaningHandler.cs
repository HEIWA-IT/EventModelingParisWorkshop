using Infrastructure;

namespace CleaningRequest.Logic
{
    public class RequestCleaningHandler : ICommandHandler<RequestCleaning>
    {
        private readonly IEventBus _eventBus;

        public RequestCleaningHandler(IEventBus eventBus)
        {
            _eventBus = eventBus;
        }

        public void Handle(RequestCleaning command)
        {
            _eventBus.Publish(new CleaningRequested(command.CleaningId, command.RoomNumber, command.Schedule));
        }
    }
}