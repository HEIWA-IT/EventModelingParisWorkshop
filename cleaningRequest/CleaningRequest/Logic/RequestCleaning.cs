using System;
using Infrastructure;

namespace CleaningRequest.Logic
{
    public class RequestCleaning : ICommand
    {
        public Guid CleaningId { get; }
        public int RoomNumber { get; }
        public DateTime Schedule { get; }

        public RequestCleaning(Guid cleaningId, int roomNumber, DateTime schedule)
        {
            CleaningId = cleaningId;
            RoomNumber = roomNumber;
            Schedule = schedule;
        }
    }
}
