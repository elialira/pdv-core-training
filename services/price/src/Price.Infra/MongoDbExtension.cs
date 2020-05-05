using EventFlow;
using EventFlow.Extensions;
using EventFlow.MongoDB.Extensions;
using EventFlow.Snapshots.Strategies;
using MongoDB.Driver;
using Price.Domain.PriceTable.Snapshots;

namespace Price.Infra
{
    public static class MongoDbExtension
    {        
        public static IEventFlowOptions ConfigureMongoDb(this IEventFlowOptions options)
        {
            // TODO: read form config
            var client = new MongoClient("mongodb://localhost:27017");

            IEventFlowOptions eventFlowOptions = options
                .ConfigureMongoDb(client, "pdv-core-training-price-service")
                .UseMongoDbSnapshotStore()
                .RegisterServices(sr => sr.Register(i => SnapshotEveryFewVersionsStrategy.Default));

            return eventFlowOptions;
        }
    }
}