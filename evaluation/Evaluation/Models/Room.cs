namespace Evaluation.Models
{
	public class Room : IAggregate
	{
        public int RoomNumber { get; set; }

        public string Status { get; set; }
	}

	public interface IAggregate
	{

	}
}
