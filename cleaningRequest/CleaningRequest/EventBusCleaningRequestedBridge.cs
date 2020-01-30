using System;
using System.Net.Http;
using System.Text;
using CleaningRequest.Logic;
using Infrastructure;
using Newtonsoft.Json;

namespace CleaningRequest
{
    class EventBusCleaningRequestedBridge : IEventHandler<CleaningRequested>
    {
        private readonly string _confirmUrl;

        public EventBusCleaningRequestedBridge(string confirmUrl)
        {
            _confirmUrl = confirmUrl;
        }

        public void Handle(CleaningRequested evt)
        {
            var client = new HttpClient();

            var httpRequestMessage = new HttpRequestMessage
            {
                Method = HttpMethod.Post,
                RequestUri = new Uri($"{_confirmUrl}eventbus/cleaningRequested"),
                Content = new StringContent(JsonConvert.SerializeObject(evt), Encoding.UTF8, "application/json")
            };
            var result = client.SendAsync(httpRequestMessage).Result;
            result.EnsureSuccessStatusCode();
        }
    }
}