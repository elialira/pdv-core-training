using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Price.Application.Interfaces;

namespace Price.Api.Controllers
{
	[ApiController]
	[Route("api/price-table")]
  public class PriceTableController : ControllerBase
  {
		private readonly IPriceTableService _priceTableService;
		public PriceTableController(IPriceTableService priceTableService)
			=> _priceTableService = priceTableService;

		[HttpPost]
		public async Task<IActionResult> CreatePriceTable(CancellationToken cancellationToken)
			=> new JsonResult(await _priceTableService.Create(cancellationToken));


		[HttpGet("{id}")]
		public async Task<IActionResult> GetById(string id, CancellationToken cancellationToken)
			=> new JsonResult(await _priceTableService.GetById(id, cancellationToken));

  }
}