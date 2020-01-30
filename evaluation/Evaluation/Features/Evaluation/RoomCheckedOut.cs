using System;
using Evaluation.Common;

namespace Evaluation.Features.Evaluation
{
	public class RoomCheckedOut : IEvent
	{
		public int RoomNumber { get; set; }
		public DateTime CheckOutTime { get; set; }
	}
}