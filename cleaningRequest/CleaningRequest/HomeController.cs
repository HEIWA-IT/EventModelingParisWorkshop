using System;
using CleaningRequest.Logic;
using Infrastructure;
using Microsoft.AspNetCore.Mvc;

namespace CleaningRequest
{
    public class HomeController : Controller
    {
        private readonly ICommandBus _commandBus;

        public HomeController(ICommandBus commandBus) => _commandBus = commandBus;

        public IActionResult Index() => View();

        [HttpPost]
        public IActionResult Index(int roomNumber, DateTime schedule)
        {
            var cleaningId = Guid.NewGuid();
            _commandBus.Handle(new RequestCleaning(cleaningId, roomNumber, schedule));
            return Redirect($"{Settings.ConfirmUrl}?cleaningId={cleaningId}");
        }
    }
}
