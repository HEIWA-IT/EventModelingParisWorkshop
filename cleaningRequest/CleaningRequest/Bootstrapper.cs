using CleaningRequest.Logic;
using Infrastructure;
using Microsoft.Extensions.DependencyInjection;

namespace CleaningRequest
{
    static class Bootstrapper
    {
        public static void Start(IServiceCollection services)
        {
            var eventBus  = new EventBus();
            var requestCleaningHandler = Decorate(new RequestCleaningHandler(eventBus));
            var eventBusCleaningRequestedBridge = new EventBusCleaningRequestedBridge(Settings.ConfirmUrl);
            var commandBus = new CommandBus();

            eventBus.Subscribe(eventBusCleaningRequestedBridge);
            commandBus.Subscribe(requestCleaningHandler);

            services.AddSingleton<ICommandBus>(commandBus);
            services.AddSingleton<IEventBus>(eventBus);
        }

        private static ICommandHandler<T> Decorate<T>(ICommandHandler<T> commandHandler) where T : ICommand
        {
            return new CommandHandlerLogger<T>(
                new CommandHandlerErrorManagement<T>(
                    commandHandler));
        }
    }
}
