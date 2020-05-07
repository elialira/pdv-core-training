using System;
using EventFlow.ValueObjects;

namespace Price.Domain.PriceTable.ValueObjects
{
	public class ValidityPeriod : ValueObject
	{
		public ValidityPeriod() { }

		public ValidityPeriod(DateTime startDate, DateTime? endDate)
		{
			StartDate = startDate;
			EndDate = endDate;
		}

		public DateTime? StartDate { get; private set; }
		public DateTime? EndDate { get; private set; }

		public bool HasValue => StartDate.HasValue;

		public bool isValid => 
			!this.HasValue || (EndDate.HasValue && StartDate >= EndDate);
		
		public bool isOnActivatedPeriod 
			=> (StartDate.HasValue && StartDate >= DateTime.Now) 
				&& (!EndDate.HasValue || EndDate <= DateTime.Now);
	}
}