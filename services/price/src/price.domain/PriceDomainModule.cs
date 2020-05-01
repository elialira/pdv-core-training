using System;
using EventFlow;
using EventFlow.Configuration;
using EventFlow.Extensions;

namespace price.domain
{
  public class PriceDomainModule : IModule
  {
    public void Register(IEventFlowOptions eventFlowOptions)
    {
        eventFlowOptions
            .AddDefaults(typeof(PriceDomainModule).Assembly);
    }
  }
}
