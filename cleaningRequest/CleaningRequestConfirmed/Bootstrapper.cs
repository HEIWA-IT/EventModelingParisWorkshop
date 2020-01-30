using CleaningRequestConfirmed.Logic;
using Infrastructure;
using Microsoft.Extensions.DependencyInjection;

namespace CleaningRequestConfirmed
{
    static class Bootstrapper
    {
        public static void Start(IServiceCollection services)
        {
            var eventBus = new EventBus();
            var cleaningRequestConfirmationStorage = 
                new Storage<CleaningRequestConfirmation>($"{Settings.StorageLocation}CleaningRequestConfirmation-{0}.json", x => x.CleaningId);
            var cleaningRequestedHandler = new CleaningRequestedHandler(cleaningRequestConfirmationStorage);
            var cleaningRequestConfirmationQuery = new CleaningRequestConfirmationQuery(cleaningRequestConfirmationStorage);
            
            eventBus.Subscribe(cleaningRequestedHandler);

            services.AddSingleton<IEventBus>(eventBus);
            services.AddSingleton(cleaningRequestConfirmationQuery);
        }
    }
}
