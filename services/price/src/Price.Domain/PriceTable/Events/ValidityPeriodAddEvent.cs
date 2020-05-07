using EventFlow.Aggregates;
using EventFlow.EventStores;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Events
{
  [EventVersion("ValidityPeriodAddEvent", 1)]
  public class ValidityPeriodAddEvent
    : AggregateEvent<PriceTable, PriceTableId>
  {
		public ValidityPeriodAddEvent(ValidityPeriod validityPeriod)
			=> ValidityPeriod = validityPeriod;
      
    public ValidityPeriod ValidityPeriod { get; set; }
  }
}