using System.Collections.Generic;
using Evaluation.Common;

namespace Evaluation.Tests
{
	public abstract class Specification<T, TC> where T : IEvent where TC : ICommand
	{
		public ICollection<IEvent> ProducedEvent { get; set; }

		public abstract T Given();

		public abstract TC When();

		protected void Setup(IHandle<TC> handler)
		{
			handler.Handle(When());
		}
	}
}