using EventFlow.Specifications;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Specifications
{
  public static partial class ValidityPeriodSpecifications
  {    
    public static ISpecification<ValidityPeriod> IsValid { get; } = new ValidityPeriodIsValidSpecification();
    public static ISpecification<ValidityPeriod> IsActive { get; } = new ValidityPeriodIsActiveSpecification();

  } 
}