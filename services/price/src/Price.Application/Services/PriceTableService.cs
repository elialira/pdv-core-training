using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using EventFlow;
using Price.Application.Interfaces;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Commands;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Application.Services
{
    public class PriceTableService : IPriceTableService
    {
        private readonly ICommandBus _commandBus;

        public PriceTableService(ICommandBus commandBus)
          => _commandBus = commandBus;

        public async Task<PriceTableId> Create(CancellationToken cancellationToken, string name,  List<ProductPrice> productPrices)
        {
            var id = PriceTableId.New;

            await _commandBus
              .PublishAsync(
                new CreatePriceTableCommand(id, name, productPrices),
                cancellationToken)
              .ConfigureAwait(false);

            return id;
        }
    }
}