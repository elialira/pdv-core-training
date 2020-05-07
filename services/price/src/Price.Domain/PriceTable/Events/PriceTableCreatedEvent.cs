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
    public PriceTableCreatedEvent(
      string name, 
      List<ProductPrice> productPrices,
      ValidityPeriod validityPeriod) 
    {
      Name = name;
      ProductPrices = productPrices;
      ValidityPeriod = validityPeriod;      
    }
                
    public string Name { get; set; }
    public List<ProductPrice> ProductPrices { get; set; }
    public ValidityPeriod ValidityPeriod { get; set; }
  }
}