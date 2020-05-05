using System;
using System.Reflection;
using EventFlow;
using EventFlow.Configuration;
using EventFlow.Extensions;
using Price.Application.Interfaces;
using Price.Application.Services;
using Price.Domain;

namespace Price.Application
{
  public class PriceApplicationModule : IModule
  {
    public static Assembly Assembly { get; } = typeof(PriceApplicationModule).Assembly;

    public void Register(IEventFlowOptions eventFlowOptions)
    {
        eventFlowOptions
            .AddDefaults(Assembly)
            .RegisterServices(sr =>
              {
                sr.Register<IPriceTableService, PriceTableService>();
              });
    }
  }
}
