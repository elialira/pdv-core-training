using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using EventFlow;
using EventFlow.Queries;
using Price.Application.Interfaces;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Commands;
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
    
    public async Task<PriceTableId> Create(CancellationToken cancellationToken)
    {
      var id = PriceTableId.New;      
      
      await _commandBus
        .PublishAsync(
          new CreatePriceTableCommand(
            id, "Tabela 1", new List<ProductPrice>(), new ValidityPeriod()
          ), 
          cancellationToken)
        .ConfigureAwait(false);
      
      return id;
    }

    public Task<IEnumerable<PriceTable>> GetAll(CancellationToken cancellationToken)
    {
      throw new NotImplementedException();
    }

    public Task<PriceTable> GetById(Guid id, CancellationToken cancellationToken)
    {
      throw new NotImplementedException();
    }

    public Task<PriceTableId> Remove(Guid id)
    {
      throw new NotImplementedException();
    }

    public Task<PriceTableId> Update(CancellationToken cancellationToken)
    {
      throw new NotImplementedException();
    }
  }
}