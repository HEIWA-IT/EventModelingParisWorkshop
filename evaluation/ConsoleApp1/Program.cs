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
			Task.Run(() =>
			{
				using (IEventStoreConnection session = EventStoreConnection.Create(new IPEndPoint(IPAddress.Loopback, 1113)))
				{
					session.ConnectAsync().Wait();

					// WRITE
					var message = new Evaluation("Super", 12);
					string serializeObject = JsonConvert.SerializeObject(message);
					byte[] bytes = Encoding.UTF8.GetBytes(serializeObject);
					var eventData = new EventData(Guid.NewGuid(), $"{nameof(Evaluation)}_v2", true, bytes, null);
					WriteResult result = session.AppendToStreamAsync($"{nameof(Evaluation)}_v2", ExpectedVersion.Any, eventData).Result;
					Console.WriteLine("Write event with data: {0}, metadata: {1}",
						Encoding.UTF8.GetString(eventData.Data),
						Encoding.UTF8.GetString(eventData.Metadata));

					// READ
					var streamEvents = session.ReadStreamEventsForwardAsync($"{nameof(Evaluation)}_v2", 0, 100, false).Result;
					foreach (ResolvedEvent resolvedEvent in streamEvents.Events)
					{
						Console.WriteLine("[{3}] Read event with data: {0}, metadata: {1}, type: {2}",
							Encoding.UTF8.GetString(resolvedEvent.Event.Data),
							Encoding.UTF8.GetString(resolvedEvent.Event.Metadata),
							resolvedEvent.Event.EventType,
							resolvedEvent.Event.EventNumber
							);
					}
				}
			}).Wait(1);

			Console.ReadKey(true);
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
