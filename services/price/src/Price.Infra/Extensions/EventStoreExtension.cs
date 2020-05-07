using System;
using System.Data.Common;
using EventFlow;
using EventFlow.EventStores.EventStore.Extensions;
using EventFlow.Extensions;
using EventFlow.MetadataProviders;
using EventStore.ClientAPI;
using EventStore.ClientAPI.SystemData;

namespace Price.Infra.Extensions
{
  public static class EventStoreExtension
  {
    public static IEventFlowOptions ConfigureEventStore(
			this IEventFlowOptions options)
    {
      string eventStoreUrl = Environment
				.GetEnvironmentVariable("EVENTSTORECONNECTION");
      
			string connectionString = 
				$"ConnectTo={eventStoreUrl}; HeartBeatTimeout=500";
      
			Uri eventStoreUri = GetUriFromConnectionString(connectionString);

      var connectionSettings = ConnectionSettings.Create()
				.EnableVerboseLogging()
				.KeepReconnecting()
				.KeepRetrying()
				.SetDefaultUserCredentials(new UserCredentials("admin", "changeit"))
				.Build();

      IEventFlowOptions eventFlowOptions = options
				.AddMetadataProvider<AddGuidMetadataProvider>()
				.UseEventStoreEventStore(eventStoreUri, connectionSettings);

      return eventFlowOptions;
    }

    private static Uri GetUriFromConnectionString(string connectionString)
    {
      DbConnectionStringBuilder builder = 
				new DbConnectionStringBuilder { ConnectionString = connectionString };
      
			string connectTo = (string)builder["ConnectTo"];
      return connectTo == null ? null : new Uri(connectTo);
    }
  }
}
