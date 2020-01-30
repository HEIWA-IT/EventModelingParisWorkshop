using System;
using System.Collections.Generic;
using System.Linq;

namespace Infrastructure
{
    public interface ICommand { }

    public interface ICommandBus
    {
        void Handle(ICommand evt);
    }

    public interface ICommandSubscriber
    {
        void Subscribe<T>(ICommandHandler<T> handler) where T : ICommand;
        void Unsubscribe<T>(ICommandHandler<T> handler) where T : ICommand;
    }

    public interface ICommandHandler<T> where T : ICommand
    {
        void Handle(T evt);
    }

    public class CommandBus : ICommandBus, ICommandSubscriber
    {
        readonly Dictionary<Type, List<CommandSubscription>> _subscribersByCommandType = new Dictionary<Type, List<CommandSubscription>>();

        public void Handle(ICommand command)
        {
            var commandType = command.GetType();

            if (_subscribersByCommandType.TryGetValue(commandType, out var handlers))
            {
                foreach (var commandSubscription in handlers)
                {
                    commandSubscription.Handle(command);
                }
            }
        }

        public void Subscribe<T>(ICommandHandler<T> handler) where T : ICommand
        {
            var commandType = typeof(T);
            if (!_subscribersByCommandType.TryGetValue(commandType, out var handlers))
            {
                handlers = new List<CommandSubscription>();
                _subscribersByCommandType[commandType] = handlers;
            }
            handlers.Add(new CommandSubscription { Subscriber = handler, Handle = command => handler.Handle((T)command) });
        }

        public void Unsubscribe<T>(ICommandHandler<T> handler) where T : ICommand
        {
            if (_subscribersByCommandType.TryGetValue(typeof(T), out var subscriberByCommandType))
            {
                foreach (var subscription in subscriberByCommandType.ToList())
                {
                    if (subscription.Subscriber == handler)
                    {
                        subscriberByCommandType.Remove(subscription);
                        break;
                    }
                }
            }
        }
    }

    class CommandSubscription
    {
        public object Subscriber { get; set; }
        public Action<ICommand> Handle { get; set; }
    }
}
