using System;
using Autofac;
using EventFlow;
using EventFlow.AspNetCore.Extensions;
using EventFlow.Autofac.Extensions;
using EventFlow.DependencyInjection.Extensions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Price.Api.swagger;
using Price.Application;
using Price.Domain;
using Price.Infra;

namespace Price.Api
{
  public class AppBootstrap
  {
    public static void AddServices(IServiceCollection services, IConfiguration configuration)
    {
      var eventFlowOptions = AddCommonServices(services);
      services.AddScoped(
				typeof(IServiceProvider),
				_ => eventFlowOptions.CreateServiceProvider());
    }

    public static IEventFlowOptions AddCommonServices(IServiceCollection services)
    {
      services.AddHttpContextAccessor();
      SwaggerServicesConfiguration.Configure(services);

      return EventFlowOptions.New
				.UseAutofacContainerBuilder(new ContainerBuilder())
        .UseServiceCollection(services)        
				.AddAspNetCore()
				.RegisterModule<PriceApplicationModule>()
        .RegisterModule<PriceDomainModule>()
        .RegisterModule<PriceInfraModule>();
    }
  }
}