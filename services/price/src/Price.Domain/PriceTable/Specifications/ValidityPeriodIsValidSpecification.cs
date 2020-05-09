using System.Collections.Generic;
using EventFlow.Specifications;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Specifications
{
  public static partial class ValidityPeriodSpecifications
  {
    private class ValidityPeriodIsValidSpecification : Specification<ValidityPeriod>
    {
      protected override IEnumerable<string> IsNotSatisfiedBecause(
        ValidityPeriod validityPeriod)
      {
        var startDate = validityPeriod.StartDate;
        var endDate = validityPeriod.EndDate;
        
        if (startDate.HasValue && startDate > endDate)
        {
          yield return $"The start date period '{validityPeriod.StartDate}' "+
            $"is greater than the end date '{validityPeriod.EndDate}'";
        }      
      }
    }
  } 
}