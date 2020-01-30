using System;

namespace Infrastructure
{
    public class CommandHandlerErrorManagement<T> : ICommandHandler<T> where T : ICommand
    {
        private readonly ICommandHandler<T> next;

        public CommandHandlerErrorManagement(ICommandHandler<T> next)
        {
            this.next = next;
        }

        public void Handle(T evt)
        {
            try
            {
                next.Handle(evt);
            }
            catch (Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
                throw;
            }
        }
    }
}