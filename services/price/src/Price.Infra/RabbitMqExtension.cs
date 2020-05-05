using System;
using EventFlow;
using EventFlow.RabbitMQ;
using EventFlow.RabbitMQ.Extensions;

namespace Price.Infra
{
    public static class RabbitMqExtension
    {
      public static EventFlow.IEventFlowOptions ConfigureRabbitMqExtension(this IEventFlowOptions options)
      {        
        // TODO: read form config
        IEventFlowOptions eventFlowOptions = options
          .PublishToRabbitMq(
            RabbitMqConfiguration.With(
              new Uri(@"amqp://test:test@localhost:5672"), 
              true, 
              4, 
              "eventflow"));

        return eventFlowOptions;
      }
    }
}