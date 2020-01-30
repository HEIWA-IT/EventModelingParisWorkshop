using Evaluation.Common;
using System;

namespace Evaluation.Features.Evaluation
{
	public class EvaluationPublished : IEvent
	{
		public Guid GuestId { get; private set; }

		public int RoomNumber { get; private set; }

		public DateTime From { get; private set; }

		public DateTime To { get; private set; }

		public string Comment { get; private set; }

		public Rating[] Ratings { get; private set; }

		public EvaluationPublished(Guid guestId, int roomNumber, DateTime from, DateTime to, string comment, Rating[] ratings)
		{
			GuestId = guestId;
			RoomNumber = roomNumber;
			From = @from;
			To = to;
			Comment = comment;
			Ratings = ratings;
		}
	}
}
