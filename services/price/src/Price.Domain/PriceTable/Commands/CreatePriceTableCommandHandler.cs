using System.Threading;
using System.Threading.Tasks;
using EventFlow.Commands;

namespace Price.Domain.PriceTable.Commands
{
  public class CreatePriceTableCommandHandler
    : CommandHandler<PriceTable, PriceTableId, CreatePriceTableCommand>
  {
    public override Task ExecuteAsync(
      PriceTable aggregate,
      CreatePriceTableCommand command,
      CancellationToken cancellationToken)
    {      
      aggregate.Create(command.Name);
      
      return Task.CompletedTask;
    }
  }
}