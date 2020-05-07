using System.Threading;
using System.Threading.Tasks;
using EventFlow.Aggregates.ExecutionResults;
using EventFlow.Commands;

namespace Price.Domain.PriceTable.Commands
{
  public class CreatePriceTableCommandHandler
    : CommandHandler<PriceTable, PriceTableId, IExecutionResult, CreatePriceTableCommand>
  {
    public override Task<IExecutionResult> ExecuteCommandAsync(
      PriceTable aggregate,
      CreatePriceTableCommand command,
      CancellationToken cancellationToken)
    {      
      var executionResult = aggregate
        .Create(command.Name, command.ProductPrices, command.ValidityPeriod);
      
      return Task.FromResult(executionResult);
    }
  }
}