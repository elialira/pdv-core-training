using EventFlow.Aggregates;
using EventFlow.EventStores;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Events
{
  [EventVersion("ProductPriceAddedEvent", 1)]
  public class ProductPriceAddedEvent
    : AggregateEvent<PriceTable, PriceTableId>
  {
		public ProductPriceAddedEvent(ProductPrice productPrice)
			=> ProductPrice = productPrice;
      
    public ProductPrice ProductPrice { get; set; }
  }
}