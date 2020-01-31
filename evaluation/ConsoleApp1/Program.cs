using EventStore.ClientAPI;
using Newtonsoft.Json;
using System;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
	class Program
	{
		static void Main()
		{
			string streamName = $"{nameof(Evaluation)}_v2";

			Task.Run(() =>
			{
				using (IEventStoreConnection session = EventStoreConnection.Create(new IPEndPoint(IPAddress.Loopback, 1113)))
				{
					session.ConnectAsync().Wait();

					// SUB
					EventStoreSubscription eventStoreSubscription = session.SubscribeToStreamAsync(streamName, false, EventAppeared).Result;

					// READ
					var streamEvents = session.ReadStreamEventsForwardAsync(streamName, 0, 100, false).Result;
					foreach (ResolvedEvent resolvedEvent in streamEvents.Events)
					{
						Print(resolvedEvent, "Read");
					}

					Console.WriteLine();

					// WRITE
					var message = new Evaluation("Super", 12);
					string serializeObject = JsonConvert.SerializeObject(message);
					byte[] bytes = Encoding.UTF8.GetBytes(serializeObject);
					var eventData = new EventData(Guid.NewGuid(), streamName, true, bytes, null);
					WriteResult result = session.AppendToStreamAsync(streamName, ExpectedVersion.Any, eventData).Result;
					Console.WriteLine("Write event with data: {0}, metadata: {1}",
						Encoding.UTF8.GetString(eventData.Data),
						Encoding.UTF8.GetString(eventData.Metadata));
				}
			}).Wait(1);

			Console.ReadKey(true);
		}

		private static Task EventAppeared(EventStoreSubscription arg1, ResolvedEvent arg2)
		{
			Print(arg2, "Receive");
			return Task.FromResult(true);
		}

		private static void Print(ResolvedEvent resolvedEvent, string prefixe)
		{
			Console.WriteLine("[{3}] {4} event with data: {0}, metadata: {1}, type: {2}",
				Encoding.UTF8.GetString(resolvedEvent.Event.Data),
				Encoding.UTF8.GetString(resolvedEvent.Event.Metadata),
				resolvedEvent.Event.EventType,
				resolvedEvent.Event.EventNumber,
				prefixe
			);
		}
	}

	public class Evaluation
	{
		public string Name { get; set; }

		public int UserId { get; set; }

		public Evaluation(string name, int userId)
		{
			Name = name;
			UserId = userId;
		}
	}
}
