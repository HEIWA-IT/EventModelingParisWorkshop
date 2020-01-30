using System;
using Infrastructure;

namespace CleaningRequestConfirmed.Logic
{
    public class CleaningRequested : IEvent
    {
        public Guid CleaningId { get; set; }
        public int RoomNumber { get; set; }
        public DateTime Schedule { get; set; }

        public CleaningRequested()
        {
            
        }

        public CleaningRequested(Guid cleaningId, int roomNumber, DateTime schedule)
        {
            CleaningId = cleaningId;
            RoomNumber = roomNumber;
            Schedule = schedule;
        }
    }
}