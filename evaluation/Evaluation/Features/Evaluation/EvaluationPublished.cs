using Evaluation.Common;
using System;
using System.Linq;

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

		public override string ToString()
		{
			return $"GuestId: {GuestId}, RoomNumber: {RoomNumber}, From: {From}, To: {To}, Comment: {Comment}, Ratings: [{string.Join(" ", Ratings.Select(r => r.ToString()))}]";
		}
	}
}
