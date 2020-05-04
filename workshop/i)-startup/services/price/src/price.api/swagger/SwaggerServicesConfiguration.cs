using System;
using System.IO;
using System.Reflection;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.OpenApi.Models;

namespace Price.Api.swagger
{
    public static class SwaggerServicesConfiguration
    {
        public static void Configure(IServiceCollection services)
        {
            services.AddSwaggerGen(
                options =>
                {
                    options.SwaggerDoc("v1", 
                        new OpenApiInfo() 
                        { 
                            Title = "Price Service API", 
                            Version = "v1" 
                        });
                });
        }
    }
}