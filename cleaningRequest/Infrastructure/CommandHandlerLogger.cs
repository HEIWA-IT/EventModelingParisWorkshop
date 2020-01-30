using System;

namespace Infrastructure
{
    public class CommandHandlerLogger<T> : ICommandHandler<T> where T : ICommand
    {
        private readonly ICommandHandler<T> next;

        public CommandHandlerLogger(ICommandHandler<T> next)
        {
            this.next = next;
        }

        public void Handle(T evt)
        {
            Console.WriteLine("Start {0}", evt);
            next.Handle(evt);
            Console.WriteLine("End {0}", evt);
        }
    }
}
