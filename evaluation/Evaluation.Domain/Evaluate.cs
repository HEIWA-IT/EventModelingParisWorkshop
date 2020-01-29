using System;
using System.Collections.Generic;

namespace Evaluation.Domain
{
	public class Evaluate : ICommand
	{
		public Guid GuestId { get; set; }

		public int RoomNumber { get; set; }

		public DateTime From { get; set; }

		public DateTime To { get; set; }

		public string Comment { get; set; }

		public List<Rating> Ratings { get; set; }
	}
}