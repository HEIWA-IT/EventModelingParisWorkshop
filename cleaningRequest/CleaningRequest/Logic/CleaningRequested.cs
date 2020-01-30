using System;
using Infrastructure;

namespace CleaningRequest.Logic
{
    public class CleaningRequested : IEvent
    {
        public Guid CleaningId { get; }
        public int RoomNumber { get; }
        public DateTime Schedule { get; }

        public CleaningRequested(Guid cleaningId, int roomNumber, DateTime schedule)
        {
            CleaningId = cleaningId;
            RoomNumber = roomNumber;
            Schedule = schedule;
        }

        protected bool Equals(CleaningRequested other)
        {
            return CleaningId.Equals(other.CleaningId) && RoomNumber == other.RoomNumber && Schedule.Equals(other.Schedule);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((CleaningRequested) obj);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(CleaningId, RoomNumber, Schedule);
        }
    }
}