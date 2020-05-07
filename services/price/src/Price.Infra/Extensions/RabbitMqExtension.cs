using System;
using EventFlow;
using EventFlow.RabbitMQ;
using EventFlow.RabbitMQ.Extensions;

namespace Price.Infra.Extensions
{
    public static class RabbitMqExtension
    {      
      public static EventFlow.IEventFlowOptions ConfigureRabbitMqExtension(
        this IEventFlowOptions options)
      {        
        string rabbitMqUrl = Environment
          .GetEnvironmentVariable("RABBITMQCONNECTION");        
        
        IEventFlowOptions eventFlowOptions = options
          .PublishToRabbitMq(
            RabbitMqConfiguration.With(
              new Uri(@rabbitMqUrl), 
              true, 
              4, 
              "eventflow"));

        return eventFlowOptions;
      }
    }
}