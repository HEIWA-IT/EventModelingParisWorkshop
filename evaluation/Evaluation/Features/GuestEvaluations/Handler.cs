using System.Collections.Generic;
using System.Linq;
using Evaluation.Features.Evaluation;

namespace Evaluation.Features.GuestEvaluations
{
	public class Handler 
        : IEventHandler<RoomCheckedOut>,
          IEventHandler<EvaluationPublished>
	{
		private readonly IList<EvaluationAggregate> _evaluationAggregates;

        public Handler()
		{
            _evaluationAggregates = new List<EvaluationAggregate>();
		}
        
		public void Apply(RoomCheckedOut @event)
        {
            _evaluationAggregates.Add(new EvaluationAggregate
            {
                GuestId = @event.GuestId,
                RoomNumber = @event.RoomNumber,
                CheckOutTime = @event.CheckOutTime,
                FullName = $"{@event.FirstName} {@event.LastName}"
            });
        }

        public void Apply(EvaluationPublished @event)
        {
            var agg = _evaluationAggregates.First(x => x.RoomNumber == @event?.RoomNumber && x.GuestId == @event.GuestId);
            agg.Evaluation = new Evaluation
            {
                Comment = @event.Comment,
                Ratings = @event.Ratings
            };
        }

    }
}
