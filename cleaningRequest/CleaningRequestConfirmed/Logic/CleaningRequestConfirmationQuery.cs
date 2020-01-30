using System;
using Infrastructure;

namespace CleaningRequestConfirmed.Logic
{
    public class CleaningRequestConfirmationQuery
    {
        private readonly Storage<CleaningRequestConfirmation> _cleaningRequestConfirmationStorage;

        public CleaningRequestConfirmationQuery(Storage<CleaningRequestConfirmation> cleaningRequestConfirmationStorage)
        {
            _cleaningRequestConfirmationStorage = cleaningRequestConfirmationStorage;
        }

        public CleaningRequestConfirmation Get(Guid cleaningId) => _cleaningRequestConfirmationStorage.Read(cleaningId);
    }
}
