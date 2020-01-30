using System;
using System.Collections.Generic;
using System.Linq;

namespace Infrastructure
{
    public interface IEvent { }

    public interface IEventBus
    {
        void Publish(IEvent evt);
    }

    public interface IEventSubscriber
    {
        void Subscribe<T>(IEventHandler<T> handler) where T : IEvent;
        void Unsubscribe<T>(IEventHandler<T> handler) where T : IEvent;
    }

    public interface IEventHandler<T> where T : IEvent
    {
        void Handle(T evt);
    }

    public class EventBus : IEventBus, IEventSubscriber
    {
        readonly Dictionary<Type, List<EventSubscription>> subscribersByEventType = new Dictionary<Type, List<EventSubscription>>();

        public void Publish(IEvent evt)
        {
            var eventType = evt.GetType();

            if (subscribersByEventType.TryGetValue(eventType, out var handlers))
            {
                foreach (var eventSubscription in handlers)
                {
                    eventSubscription.Handle(evt);
                }
            }
        }

        public void Subscribe<T>(IEventHandler<T> handler) where T : IEvent
        {
            var eventType = typeof(T);
            if (!subscribersByEventType.TryGetValue(eventType, out var handlers))
            {
                handlers = new List<EventSubscription>();
                subscribersByEventType[eventType] = handlers;
            }
            handlers.Add(new EventSubscription { Subscriber = handler, Handle = evt => handler.Handle((T)evt) });
        }

        public void Unsubscribe<T>(IEventHandler<T> handler) where T : IEvent
        {
            if (subscribersByEventType.TryGetValue(typeof(T), out var subscriberByEventType))
            {
                foreach (var subscription in subscriberByEventType.ToList())
                {
                    if (subscription.Subscriber == handler)
                    {
                        subscriberByEventType.Remove(subscription);
                        break;
                    }
                }
            }
        }
    }

    class EventSubscription
    {
        public object Subscriber { get; set; }
        public Action<IEvent> Handle { get; set; }
    }
}
