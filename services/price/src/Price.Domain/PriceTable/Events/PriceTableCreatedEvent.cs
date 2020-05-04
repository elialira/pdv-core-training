using EventFlow.Aggregates;
using EventFlow.EventStores;

namespace Price.Domain.PriceTable.Events
{
  [EventVersion("PriceTableRegisteredEvent", 1)]
  public class PriceTableCreatedEvent 
    : AggregateEvent<PriceTable, PriceTableId>
  {
    public PriceTableCreatedEvent(string name) 
      => Name = name;
      
    public string Name { get; set; }
  }
}