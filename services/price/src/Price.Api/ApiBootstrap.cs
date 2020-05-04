using System;
using EventFlow;
using EventFlow.AspNetCore.Extensions;
using EventFlow.DependencyInjection.Extensions;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Price.Api.swagger;
using Price.Application;
using Price.Domain;

namespace Price.Api
{
    public class ApiBootstrap
    {
        public static void AddServices(IServiceCollection services, IConfiguration configuration)
        {
            var eventFlowOptions = AddCommonServices(services);
            services.AddScoped(
                typeof(IServiceProvider), 
                _ => eventFlowOptions.CreateServiceProvider()
            );
        }

        public static IEventFlowOptions AddCommonServices(IServiceCollection services)
        {
            services.AddHttpContextAccessor();
            SwaggerServicesConfiguration.Configure(services);

            return EventFlowOptions.New
                .UseServiceCollection(services)
                .AddAspNetCore(options => { options.AddUserClaimsMetadata(); })
                .RegisterModule<PriceApplicationModule>()
                .RegisterModule<PriceDomainModule>();
        }

    }
}