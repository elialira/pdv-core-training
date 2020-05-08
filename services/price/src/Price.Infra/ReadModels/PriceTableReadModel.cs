using System.Collections.Generic;
using EventFlow.Aggregates;
using EventFlow.ReadStores;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Infra.ReadModels
{
  public class PriceTableReadModel : 
    IReadModel, 
    IAmReadModelFor<PriceTable, PriceTableId, PriceTableCreatedEvent>,
    IAmReadModelFor<PriceTable, PriceTableId, ValidityPeriodUpdatedEvent>
  {
    public PriceTableId Id { get; set; }
    public string Name { get; set; }
    public List<ProductPrice> ProductPrices { get; set; }
    public ValidityPeriod ValidityPeriod { get; set; }

    public void Apply(
			IReadModelContext context, 
			IDomainEvent<PriceTable, PriceTableId, PriceTableCreatedEvent> domainEvent)
    {
      var aggEvent = domainEvent.AggregateEvent;
			
			Id = domainEvent.AggregateIdentity;
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
