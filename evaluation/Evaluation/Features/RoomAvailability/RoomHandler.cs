using Evaluation.Models;
using System.Collections.Generic;
using System.Linq;
using Evaluation.Features.RoomAvailability;

namespace Evaluation.Features.Evaluation
{
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
}
