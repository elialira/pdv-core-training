using System.Collections.Generic;
using EventFlow.Aggregates.ExecutionResults;
using EventFlow.Commands;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Commands
{
  public class CreatePriceTableCommand : Command<PriceTable, PriceTableId, IExecutionResult>
  {
    public string Name { get; }
    public List<ProductPrice> ProductPrices { get; set; }
    public CreatePriceTableCommand(
      PriceTableId aggregateId, string name, List<ProductPrice> productPrices) 
      : base(aggregateId)
    {
        Name = name;
        ProductPrices = productPrices;
    }
  }
}