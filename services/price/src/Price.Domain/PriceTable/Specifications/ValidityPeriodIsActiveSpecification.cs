using System;
using System.Collections.Generic;
using EventFlow.Specifications;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Specifications
{
  public static partial class ValidityPeriodSpecifications
  {
    private class ValidityPeriodIsActiveSpecification : Specification<ValidityPeriod>
    {
      protected override IEnumerable<string> IsNotSatisfiedBecause(
        ValidityPeriod validityPeriod)
      {
        var startDate = validityPeriod.StartDate;
        var endDate = validityPeriod.EndDate;
        var currentDate = DateTime.Now;

        if (!startDate.HasValue)
        {
          yield return "The period is not activated because " +
            "the start date period has no value";
        }      

        if (startDate > currentDate)
        {
          yield return $"The period is not activated because the current " +
            $"date '{currentDate}' is greater than the start date '{startDate}'";
        }      

        if (currentDate > endDate)
        {
          yield return $"The period is not activated because the current " +
            $"date '{currentDate}' is greater than the end date '{startDate}'";
        }      
      }
    }
  } 
}