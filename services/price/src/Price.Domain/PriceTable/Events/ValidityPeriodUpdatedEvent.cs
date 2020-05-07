using EventFlow.Aggregates;
using EventFlow.EventStores;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Events
{
  [EventVersion("ValidityPeriodUpdatedEvent", 1)]
  public class ValidityPeriodUpdatedEvent
    : AggregateEvent<PriceTable, PriceTableId>
  {
		public ValidityPeriodUpdatedEvent(ValidityPeriod validityPeriod)
			=> ValidityPeriod = validityPeriod;
      
    public ValidityPeriod ValidityPeriod { get; set; }
  }
}