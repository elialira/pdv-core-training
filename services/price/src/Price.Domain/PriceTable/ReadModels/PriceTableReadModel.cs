using System.Collections.Generic;
using EventFlow.Aggregates;
using EventFlow.ReadStores;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.ReadModels
{
  public class PriceTableReadModel : IReadModel,
    IAmReadModelFor<PriceTable, PriceTableId, PriceTableCreatedEvent>,
    IAmReadModelFor<PriceTable, PriceTableId, ValidityPeriodUpdatedEvent>
  {
    public string Name { get; set; }
    public List<ProductPrice> ProductPrices { get; set; }
    public ValidityPeriod ValidityPeriod { get; set; }

    public void Apply(
			IReadModelContext context, 
			IDomainEvent<PriceTable, PriceTableId, PriceTableCreatedEvent> domainEvent)
    {
      var aggEvent = domainEvent.AggregateEvent;
			
			Name = aggEvent.Name;
			ProductPrices = aggEvent.ProductPrices;
			ValidityPeriod = aggEvent.ValidityPeriod;
    }

    public void Apply(
      IReadModelContext context, 
      IDomainEvent<PriceTable, PriceTableId, ValidityPeriodUpdatedEvent> domainEvent)
    {
      ValidityPeriod = domainEvent.AggregateEvent.ValidityPeriod;
    }
  }
}
