using System.Collections.Generic;
using System.Linq;
using EventFlow.Aggregates;
using EventFlow.EventStores;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Events
{
  [EventVersion("PriceTableRegisteredEvent", 1)]
  public class PriceTableCreatedEvent 
    : AggregateEvent<PriceTable, PriceTableId>
  {
    public PriceTableCreatedEvent(string name, List<ProductPrice> productPrices) 
    {
      Name = name;
      ProductPrices = productPrices;
    }
                
    public string Name { get; set; }
    public List<ProductPrice> ProductPrices { get; set; }
  }
}