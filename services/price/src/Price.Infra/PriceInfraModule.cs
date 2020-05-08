using System;
using System.Reflection;
using EventFlow;
using EventFlow.Configuration;
using EventFlow.Extensions;
using Price.Infra.Extensions;

namespace Price.Infra
{
  public class PriceInfraModule : IModule
  {
    public static Assembly Assembly { get; } = typeof(PriceInfraModule).Assembly;

    public void Register(IEventFlowOptions eventFlowOptions)
    {
      eventFlowOptions
        .AddDefaults(Assembly)
        .ConfigureEventStore()
        .ConfigureRabbitMqExtension()
        .ConfigureInMemoryReadModel();
    }
  }
}
