using System.Collections.Generic;
using EventFlow.Aggregates;
using EventFlow.Specifications;
using Price.Domain.Specs;

namespace Price.Domain.PriceTable.Specifications
{
  public static class PriceTableSpecifications
  {
    public static ISpecification<IAggregateRoot> IsNew => AggregateSpecifications.IsNew;
    public static ISpecification<IAggregateRoot> IsCreated => AggregateSpecifications.IsCreated;
    public static ISpecification<IAggregateRoot> HasValidPeriod { get; } = new PriceTableHasValidPeriodSpecification();
    
    private class PriceTableHasValidPeriodSpecification : Specification<IAggregateRoot>
    {
      protected override IEnumerable<string> IsNotSatisfiedBecause(IAggregateRoot obj) 
        => ValidityPeriodSpecifications.IsValid.WhyIsNotSatisfiedBy(((PriceTable)obj).ValidityPeriod);
    }
  } 
}