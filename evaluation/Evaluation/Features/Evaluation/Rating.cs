namespace Evaluation.Features.Evaluation
{
	public class Rating
	{
		public string RateType { get; set; }

		public int Rate { get; set; }

		public override string ToString()
		{
			return $"RatingType: {RateType}, Rate: {Rate}";
		}
	}
}
