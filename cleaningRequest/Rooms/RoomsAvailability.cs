using System;
using System.Collections.Generic;
using System.Linq;
using Infrastructure;

namespace Rooms
{
    public class RoomsAvailability
    {
        public IList<Room> Rooms { get; set; } = new List<Room>();
        public DateTime AtDate { get; set; }
    }

    public class Room
    {
        public string Number { get; set; }
    }

    public class RoomReserved : IEvent
    {
        public string RoomNumber { get; set; }
        public DateTime FromDate { get; set; }
        public DateTime ToDate { get; set; }
    }

    public interface IRoomsAvailabilityAccessor
    {
        RoomsAvailability GetAt(DateTime date);
        void Save(RoomsAvailability roomsAvailability);
    }

    public class RoomReservedHandler :
        IEventHandler<RoomReserved>
    {
        private readonly IRoomsAvailabilityAccessor roomsAvailabilityAccessor;

        public RoomReservedHandler(IRoomsAvailabilityAccessor roomsAvailabilityAccessor)
        {
            this.roomsAvailabilityAccessor = roomsAvailabilityAccessor;
        }

        public void Handle(RoomReserved evt)
        {
            var dates = Enumerable.Range(0, (int)(evt.ToDate - evt.FromDate).TotalDays + 1)
                .Select(x => evt.FromDate.AddDays(x));
            foreach (var date in dates)
            {
                var availability = roomsAvailabilityAccessor.GetAt(date);
                availability.Rooms.Add(new Room { Number = evt.RoomNumber });
                roomsAvailabilityAccessor.Save(availability);
            }
        }
    }
}
