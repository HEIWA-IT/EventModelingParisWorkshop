using System;
using Evaluation.Common;

namespace Evaluation.Features.EvaluationEnable
{
    public class EvaluationEnable : ICommand
    {
        public Guid GuestId { get; set; }

        public int BookingNumber { get; set; }
    }
}