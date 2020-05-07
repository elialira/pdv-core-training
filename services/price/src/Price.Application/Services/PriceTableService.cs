using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using EventFlow;
using EventFlow.Queries;
using Price.Application.Interfaces;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Commands;
using Price.Domain.PriceTable.ReadModels;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Application.Services
{
    public class PriceTableService : IPriceTableService
    {
        private readonly ICommandBus _commandBus;
        private readonly IQueryProcessor _queryProcessor;

        public PriceTableService(
          ICommandBus commandBus,
          IQueryProcessor queryProcessor)
        {
            _commandBus = commandBus;
            _queryProcessor = queryProcessor;
        }

        public async Task<PriceTableId> Create(CancellationToken cancellationToken, string name, List<ProductPrice> productPrices)
        {
            var id = PriceTableId.New;

            await _commandBus
              .PublishAsync(
                new CreatePriceTableCommand(id, name, productPrices, new ValidityPeriod()),
                cancellationToken)
              .ConfigureAwait(false);

            return id;
        }

        public Task<PriceTableId> Remove(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<PriceTableId> Update(CancellationToken cancellationToken)
        {
            throw new NotImplementedException();
        }

        async Task<PriceTableReadModel> IPriceTableService.GetById(string id, CancellationToken cancellationToken)
        {
            var readModel = await _queryProcessor.ProcessAsync(
              new ReadModelByIdQuery<PriceTableReadModel>(id), cancellationToken);

            return readModel;
        }

        Task<IEnumerable<PriceTableReadModel>> IPriceTableService.GetAll(CancellationToken cancellationToken)
        {
            throw new NotImplementedException();
        }
    }
}