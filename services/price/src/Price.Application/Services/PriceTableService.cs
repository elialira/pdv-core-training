using System.Threading;
using System.Threading.Tasks;
using EventFlow;
using Price.Application.Interfaces;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Commands;

namespace Price.Application.Services
{
  public class PriceTableService : IPriceTableService
  {
    private readonly ICommandBus _commandBus;
    
    public PriceTableService(ICommandBus commandBus)
      => _commandBus = commandBus;
    
    public async Task<PriceTableId> Create(CancellationToken cancellationToken)
    {
      var priceTableId = PriceTableId.New;
      
      await _commandBus.PublishAsync(
        new CreatePriceTableCommand(priceTableId, "Tabela 1"), 
        cancellationToken);

      return priceTableId;
    }
  }
}