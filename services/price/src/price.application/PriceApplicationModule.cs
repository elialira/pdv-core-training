using System;
using EventFlow;
using EventFlow.Configuration;
using EventFlow.Extensions;

namespace price.application
{
  public class PriceApplicationModule : IModule
  {
    public void Register(IEventFlowOptions eventFlowOptions)
    {
        eventFlowOptions
            .AddDefaults(typeof(PriceApplicationModule).Assembly);
    }
  }
}
