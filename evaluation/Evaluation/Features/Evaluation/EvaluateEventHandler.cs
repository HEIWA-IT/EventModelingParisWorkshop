using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Security.AccessControl;
using Evaluation.Common;
using Evaluation.Models;

namespace Evaluation.Features.Evaluation
{
    public interface IRoomEventHandler<T> where T : IEvent
    {
        void Apply(T @event);
    }

    public class RoomHandler : IRoomEventHandler<RoomCheckedIn>, IRoomEventHandler<RoomCheckedOut>
    {
        private readonly IList<Room> _rooms;

        public RoomHandler()
        {
            _rooms = new List<Room>() { new Room { RoomNumber = 11, Status = "Free" } };
        }

        public void Apply(RoomCheckedIn @event)
        {
            _rooms.First(x => x.RoomNumber == @event?.RoomNumber).Status = "CheckIn";
        }

        public void Apply(RoomCheckedOut @event)
        {
            _rooms.First(x => x.RoomNumber == @event?.RoomNumber).Status = "CheckOut";
        }

        public IList<Room> Projection()
        {
            return _rooms;
        }


    }

    public class RoomCheckedOut : IEvent
    {
        public int RoomNumber { get; set; }
        public DateTime CheckOutTime { get; set; }
    }


    public class RoomCheckedIn : IEvent
    {
        public string CustomerFirstName { get; set; }
        public string CustomerLastName { get; set; }
        public int RoomNumber { get; set; }
        public DateTime CheckInTime { get; set; }
        public DateTime BadgeNumber { get; set; }
        public string ReservationNumber { get; set; }
    }

}
