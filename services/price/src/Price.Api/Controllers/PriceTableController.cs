using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using AutoMapper;
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
        private readonly IMapper _mapper;

        public PriceTableController(IPriceTableService priceTableService, IMapper mapper)
        {
            _priceTableService = priceTableService;
            _mapper = mapper;
        }

        [HttpPost]
        public async Task<IActionResult> CreatePriceTable(CancellationToken cancellationToken, [FromBody]PriceTableViewModel viewModel) =>
            new JsonResult(await _priceTableService.Create(cancellationToken, viewModel.Name, _mapper.Map<List<ProductPrice>>(viewModel.ProductPrices)));

    }
}