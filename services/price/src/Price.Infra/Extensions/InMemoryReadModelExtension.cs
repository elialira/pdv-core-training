using EventFlow;
using EventFlow.Extensions;
using Price.Infra.ReadModels;

namespace Price.Infra.Extensions
{
    public static class InMemoryReadModelExtension
    {      
      public static EventFlow.IEventFlowOptions ConfigureInMemoryReadModel(
        this IEventFlowOptions options)
      {        
        IEventFlowOptions eventFlowOptions = options
           .UseInMemoryReadStoreFor<PriceTableReadModel>()
           .UseInMemoryReadStoreFor<ValidityPeriodReadModel>();
        
        return eventFlowOptions;
      }
    }
}