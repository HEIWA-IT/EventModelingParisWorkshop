using System.Threading.Tasks;

namespace Evaluation.Domain
{
	public class EvaluateHandler : IHandles<Evaluate>
	{
		private readonly IEvalutationRepository _repo;

		public EvaluateHandler(IEvalutationRepository repository)
		{
			_repo = repository;
		}

		public async Task Handle(Evaluate command)
		{
			var evt = new ReviewPublished
			{
				GuestId = command.GuestId,
				RoomNumber = command.RoomNumber,
				From = command.From,
				To = command.To,
				Comment = command.Comment,
				Ratings = command.Ratings
			};

			await _repo.Save(evt);
		}
	}
}
