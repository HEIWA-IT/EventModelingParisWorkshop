using System;
using CleaningRequestConfirmed.Logic;
using Microsoft.AspNetCore.Mvc;

namespace CleaningRequestConfirmed
{
    public class HomeController : Controller
    {
        private readonly CleaningRequestConfirmationQuery _query;

        public HomeController(CleaningRequestConfirmationQuery query) => _query = query;

        public IActionResult Index(Guid cleaningId) => View(_query.Get(cleaningId));
    }
}
