using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Timers;
using Evaluation.Common;
using EventStore.ClientAPI;
using EventStore.ClientAPI.SystemData;
using Microsoft.Extensions.Hosting;
using Newtonsoft.Json;

namespace Evaluation.Features.Evaluation
{
    public class EvaluationReaderBackgroundService : BackgroundService
    {
        private IEventStoreConnection connection;
        private EventStoreSubscription sub;
        public EvaluationReaderBackgroundService()
        {
            var connString = "ConnectTo=tcp://admin:changeit@localhost:1113; HeartBeatTimeout=500;";
            connection = EventStoreConnection.Create(connString);

        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {

            await connection.ConnectAsync();

            sub = await connection.SubscribeToStreamAsync("evaluation", false, EventAppeared, SubscriptionDropped);

            try
            {
                var slices = await connection.ReadAllEventsForwardAsync(Position.Start, 10, false, new UserCredentials("admin", "changeit"));

                foreach (var @event in slices.Events)
                {

                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
         
        }

        private void SubscriptionDropped(EventStoreSubscription arg1, SubscriptionDropReason arg2, Exception arg3)
        {
            throw new NotImplementedException();
        }

        private Task EventAppeared(EventStoreSubscription arg1, ResolvedEvent arg2)
        {
            var type = Assembly.GetExecutingAssembly().GetType(arg2.Event.EventType);
            var @event = JsonConvert.DeserializeObject(Encoding.UTF8.GetString(arg2.Event.Data), type) as IEvent;

            return Task.CompletedTask;
        }
    }
}
