using System;
using System.Reflection;
using EventFlow;
using EventFlow.Configuration;
using EventFlow.Extensions;

namespace Price.Domain
{
  public class PriceDomainModule : IModule
  {
    public static Assembly Assembly { get; } = typeof(PriceDomainModule).Assembly;
    public void Register(IEventFlowOptions eventFlowOptions)
    {
        eventFlowOptions
            .AddDefaults(Assembly);
    }
  }
}
