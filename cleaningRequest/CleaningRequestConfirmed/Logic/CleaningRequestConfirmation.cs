using System;

namespace CleaningRequestConfirmed.Logic
{
    public class CleaningRequestConfirmation
    {
        public Guid CleaningId { get; }
        public int RoomNumber { get; }
        public DateTime Schedule { get; }

        public CleaningRequestConfirmation(Guid cleaningId, int roomNumber, DateTime schedule)
        {
            CleaningId = cleaningId;
            RoomNumber = roomNumber;
            Schedule = schedule;
        }
    }
}
