using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading.Tasks;
using Evaluation.Common;
using Evaluation.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace Evaluation.Features.Evaluation
{
    public class EvaluationController : Controller
    {
        private readonly ILogger<EvaluationController> _logger;
        private readonly IHandle<Evaluate> _handler;

        public EvaluationController(ILogger<EvaluationController> logger, IHandle<Evaluate> handler)
        {
            _logger = logger;
            _handler = handler;
        }

        public IActionResult Index()
        {
            var command = new Evaluate
            {
                Comment = "Great Hotel",
                From = new DateTime(2020, 1, 29),
                To = new DateTime(2020, 2, 5),
                GuestId = Guid.NewGuid(),
                RoomNumber = 1,
                Ratings = new[] {
                    new Rating { RateType = "Services", Rate = 1},
                    new Rating { RateType = "Cleaness", Rate = 1},
                    new Rating { RateType = "Food", Rate = 1 }
                }
            };

            return View(command);
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        [HttpPost]
        public async Task<IActionResult> Publish(Evaluate evaluate)
        {
            await _handler.Handle(evaluate);
            return RedirectToAction(nameof(Thanks));
        }

        public IActionResult Thanks()
        {
            return View();
        }
    }
}
