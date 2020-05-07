using System;
using EventFlow.ValueObjects;

namespace Price.Domain.PriceTable.ValueObjects
{
	public class ValidityPeriod : ValueObject
	{
		public ValidityPeriod() 
		{ 
			StartDate = new DateTime();
			EndDate = new DateTime();
		}

		public ValidityPeriod(DateTime? startDate, DateTime? endDate)
		{
			StartDate = startDate;
			EndDate = endDate;
		}

		public DateTime? StartDate { get; private set; }
		public DateTime? EndDate { get; private set; }

		public bool isOnValidityPeriod()
		{
			return (StartDate.HasValue && StartDate >= DateTime.Now) 
				&& (!EndDate.HasValue || EndDate <= DateTime.Now);
		}
	}
}