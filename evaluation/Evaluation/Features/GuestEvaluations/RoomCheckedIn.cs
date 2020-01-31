using System;
using Evaluation.Common;

namespace Evaluation.Features.GuestEvaluations
{
	public class RoomCheckedIn : IEvent
	{
        public Guid GuestId { get; set; }
		public string CustomerFirstName { get; set; }
		public string CustomerLastName { get; set; }
		public int RoomNumber { get; set; }
		public DateTime CheckInTime { get; set; }
		public DateTime BadgeNumber { get; set; }
		public string ReservationNumber { get; set; }
	}
}