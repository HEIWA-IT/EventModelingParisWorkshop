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
            var requestCleaningHandler = new RequestCleaningHandler(eventBus);
            var eventBusCleaningRequestedBridge = new EventBusCleaningRequestedBridge(Settings.ConfirmUrl);
            var commandBus = new CommandBus();

            eventBus.Subscribe(eventBusCleaningRequestedBridge);
            commandBus.Subscribe(requestCleaningHandler);

            services.AddSingleton<ICommandBus>(commandBus);
            services.AddSingleton<IEventBus>(eventBus);
        }
    }
}
