using System;
using Evaluation.Common;
using System.Collections.Generic;
using System.Net;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using EventStore.ClientAPI;
using Newtonsoft.Json;

namespace Evaluation.Features.Evaluation
{
	public interface IEvaluationRepository
	{
		Task Save(IEvent @event);

		IEnumerable<IEvent> GetEvents();

     
    }

    public class EventStoreRepository : IEvaluationRepository
    {
        public EventStoreRepository()
        {
            
        }

        public async Task Save(IEvent @event)
        {
            var connection = EventStoreConnection.Create(new IPEndPoint(IPAddress.Loopback, 1113));

            

            await connection.ConnectAsync();

            var data = JsonConvert.SerializeObject(@event);

            var myEvent = new EventData(Guid.NewGuid(), @event.GetType().FullName, true,
                Encoding.UTF8.GetBytes(data), null);
            try
            {
                await connection.AppendToStreamAsync("evaluation", ExpectedVersion.Any, myEvent);

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
        }

        public IEnumerable<IEvent> GetEvents()
        {
            var connection = EventStoreConnection.Create(new IPEndPoint(IPAddress.Loopback, 1113));
            
            connection.ConnectAsync().Wait();

            var streamEvents =
                connection.ReadStreamEventsForwardAsync("evaluation", 0, 1, false).Result;

            var returnedEvent = streamEvents.Events[0].Event;

            var type = Assembly.GetExecutingAssembly().GetType(returnedEvent.EventType);
            var @event  = JsonConvert.DeserializeObject(Encoding.UTF8.GetString(returnedEvent.Data), type) as IEvent;
            yield return @event;
        }
    }
}
