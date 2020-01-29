namespace Evaluation.Domain
{
    public interface IEvent
    {
    }

    public class ReviewPublished : IEvent 
    {
        public Guid GuestId { get; set; }

        public int RoomNumber { get; set; }

        public DateTime From { get; set; }

        public DateTime To { get; set; }

        public string Comment { get; set; }

        public List<Rating> Ratings { get; set; }
    }
}