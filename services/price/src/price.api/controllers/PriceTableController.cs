using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Price.Application.Interfaces;

namespace Price.Api.Controllers
{
	[ApiController]
	[Route("api/[controller]")]
  public class PriceTableController : ControllerBase
  {
		private readonly IPriceTableService _priceTableService;
		public PriceTableController(IPriceTableService priceTableService)
			=> _priceTableService = priceTableService;

		[HttpPost]
		public async Task<IActionResult> CreatePriceTable(CancellationToken cancellationToken)
			=> new JsonResult(await _priceTableService.Create(cancellationToken));
  }
}