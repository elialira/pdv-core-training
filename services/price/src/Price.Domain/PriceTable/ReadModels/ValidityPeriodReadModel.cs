using System;
using EventFlow.Aggregates;
using EventFlow.ReadStores;
using Price.Domain.PriceTable.Events;

namespace Price.Domain.PriceTable.ReadModels
{
  public class ValidityPeriodReadModel : IReadModel,
      IAmReadModelFor<PriceTable, PriceTableId, PriceTableCreatedEvent>,
      IAmReadModelFor<PriceTable, PriceTableId, ValidityPeriodUpdatedEvent>
  {
    public DateTime? StartDate { get; set; }
    public DateTime? EndDate { get; set; }

    public void Apply(
      IReadModelContext context, 
      IDomainEvent<PriceTable, PriceTableId, ValidityPeriodUpdatedEvent> domainEvent)
    {
      var validity = domainEvent.AggregateEvent.ValidityPeriod;
      StartDate = validity.StartDate;
      EndDate = validity.EndDate;
    }

    public void Apply(
      IReadModelContext context, 
      IDomainEvent<PriceTable, PriceTableId, PriceTableCreatedEvent> domainEvent)
    {
      var validity = domainEvent.AggregateEvent.ValidityPeriod;
      StartDate = validity.StartDate;
      EndDate = validity.EndDate;
    }
  }
}