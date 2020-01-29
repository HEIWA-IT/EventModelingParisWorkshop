using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Domain
{

    public interface ICommand
    {

    }

    public class Rating
    {
        public string RateType { get; set; }

        public int Rate { get; set; }
    }

    public class Evaluate : ICommand
    {
        public Guid GuestId { get; set; }

        public int RoomNumber { get; set; }

        public DateTime From { get; set; }

        public DateTime To { get; set; }

        public string Comment { get; set; }

        public List<Rating> Ratings { get; set; }
    }

    public interface IHandles<T> where T: ICommand
    {
        Task Handle(T command);
    }

    public class EvaluateHandler : IHandles<Evaluate>
    {
        private IEvalutationRepository _repo;

        public EvaluateHandler(IEvalutationRepository repository)
        {
            _repo = repository;
        }

        public async Task Handle(Evaluate command)
        {
            var evt =  new ReviewPublished 
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

    public interface IEvalutationRepository
    {
        Task Save(IEvent @event);
    }
}
