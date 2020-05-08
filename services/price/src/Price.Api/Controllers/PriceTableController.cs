using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Price.Application.Interfaces;
using Price.Application.ViewModels;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Api.Controllers
{
  [ApiController]
  [Route("api/price-table")]
  public class PriceTableController : ControllerBase
  {
    private readonly IPriceTableService _priceTableService;

    public PriceTableController(IPriceTableService priceTableService)
    {
      _priceTableService = priceTableService;
    }

    [HttpPost]
    public async Task<IActionResult> CreatePriceTable(
      [FromBody] PriceTableViewModel viewModel, CancellationToken cancellationToken)
        => new JsonResult(await _priceTableService.Create(viewModel,cancellationToken));

    [HttpGet]
    public async Task<IActionResult> GetAll(CancellationToken cancellationToken)
      => new JsonResult(await _priceTableService.GetAll(cancellationToken));

    [HttpGet("{id}")]
    public async Task<IActionResult> GetById(string id, CancellationToken cancellationToken)
      => new JsonResult(await _priceTableService.GetById(id, cancellationToken));
  }
}