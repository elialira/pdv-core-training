using System;
using EventFlow.ValueObjects;
using Price.Domain.PriceTable.Specifications;

namespace Price.Domain.PriceTable.ValueObjects
{
	public class ValidityPeriod : ValueObject
	{
		public ValidityPeriod(DateTime? startDate, DateTime? endDate)
		{
			StartDate = startDate;
			EndDate = endDate;
		}

		public DateTime? StartDate { get; private set; }
		public DateTime? EndDate { get; private set; }
		
		public bool isValid 
			=> ValidityPeriodSpecifications.IsValid.IsSatisfiedBy(this);

		public bool isActive 
			=> ValidityPeriodSpecifications.IsActive.IsSatisfiedBy(this);

	}
}