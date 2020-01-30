using System;
using Evaluation.Common;

namespace Evaluation.Features.Evaluation
{
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