using System.Collections.Generic;
using EventFlow.Aggregates;
using EventFlow.Provided.Specifications;
using EventFlow.Specifications;

namespace Price.Domain.Specs
{
  public static class AggregateSpecifications
  {
    public static ISpecification<IAggregateRoot> IsNew { get; } = new AggregateIsNewSpecification();
    public static ISpecification<IAggregateRoot> IsCreated { get; } = new AggregateIsCreatedSpecification();
    private class AggregateIsCreatedSpecification : Specification<IAggregateRoot>
    {
      protected override IEnumerable<string> IsNotSatisfiedBecause(IAggregateRoot obj)
      {
        if (obj.IsNew)
        {
          yield return $"Aggregate '{obj.Name}' with ID '{obj.GetIdentity()}' is new";
        }
      }
    }
  }
}