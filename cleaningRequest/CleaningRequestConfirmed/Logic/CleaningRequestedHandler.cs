using Infrastructure;

namespace CleaningRequestConfirmed.Logic
{
    class CleaningRequestedHandler : IEventHandler<CleaningRequested>
    {
        private readonly Storage<CleaningRequestConfirmation> _cleaningRequestConfirmationStorage;

        public CleaningRequestedHandler(Storage<CleaningRequestConfirmation> cleaningRequestConfirmationStorage)
        {
            _cleaningRequestConfirmationStorage = cleaningRequestConfirmationStorage;
        }

        public void Handle(CleaningRequested evt)
        {
            var readModel = new CleaningRequestConfirmation(evt.CleaningId, evt.RoomNumber, evt.Schedule);
            _cleaningRequestConfirmationStorage.Save(readModel);
        }
    }
}