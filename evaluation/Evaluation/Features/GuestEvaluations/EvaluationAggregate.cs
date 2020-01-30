using System;
using Evaluation.Models;

namespace Evaluation.Features.GuestEvaluations
{
    public class EvaluationAggregate : IAggregate
    {
        public Guid GuestId { get; set; }
        public string FullName { get; set; }
        public int RoomNumber { get; set; }
        public DateTime CheckOutTime { get; set; }
        public Evaluation Evaluation { get; set; }
    }
}