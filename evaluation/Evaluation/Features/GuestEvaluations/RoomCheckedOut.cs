using System;
using Evaluation.Common;

namespace Evaluation.Features.GuestEvaluations
{
	public class RoomCheckedOut : IEvent
	{
        public Guid GuestId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
		public int RoomNumber { get; set; }
		public DateTime CheckOutTime { get; set; }
	}
}