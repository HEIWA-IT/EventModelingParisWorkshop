using System;
using Evaluation.Common;

namespace Evaluation.Features.EvaluationEnable
{
    public class EvaluationEnabled : IEvent
    {
        public Guid GuestId { get; private set; }

        public int BookingNumber { get; private set; }

        public bool IsAuthorized { get; set; }

        public EvaluationEnabled(Guid guestId, int bookingNumber)
        {
            GuestId = guestId;
            BookingNumber = bookingNumber;
            IsAuthorized = true;
        }
    }
}