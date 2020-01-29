using System;
using Evaluation.Common;

namespace Evaluation.Features.Evaluation
{
    public class Evaluate : ICommand
    {
        public Guid GuestId { get; set; }

        public int RoomNumber { get; set; }

        public DateTime From { get; set; }

        public DateTime To { get; set; }

        public string Comment { get; set; }

        public Rating[] Ratings { get; set; }
     
    }
}
