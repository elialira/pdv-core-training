using EventFlow.Commands;
using Price.Domain.PriceTable;

namespace Price.Domain.PriceTable.Commands
{
  public class CreatePriceTableCommand : Command<PriceTable, PriceTableId>
  {
    public string Name { get; }
    public CreatePriceTableCommand(PriceTableId aggregateId, string name) 
      : base(aggregateId)
    {
        Name = name;
    }
  }
}