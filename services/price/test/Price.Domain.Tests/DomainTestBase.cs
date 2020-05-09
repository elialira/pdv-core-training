using System;
using System.Threading;
using System.Threading.Tasks;
using EventFlow;
using EventFlow.Aggregates;
using EventFlow.Configuration;
using EventFlow.Core;
using EventFlow.DependencyInjection.Extensions;
using Microsoft.Extensions.DependencyInjection;
using Price.Test.Common;

namespace Price.Domain.Tests
{
	public class DomainTestBase : IDisposable
{
		protected readonly IRootResolver Resolver;
		protected readonly IAggregateStore AggregateStore;

		public DomainTestBase()
		{
			var services = new ServiceCollection();
			ConfigurationRootCreator.Create(services);
			
			Resolver = EventFlowOptions.New
				.UseServiceCollection(services)
				.RegisterModule<PriceDomainModule>()                
				.CreateResolver();

			AggregateStore = Resolver.Resolve<IAggregateStore>();
		}

		protected async Task UpdateAsync<TAggregate, TIdentity>(
			TIdentity id, 
			Action<TAggregate> action)
				where TAggregate : IAggregateRoot<TIdentity>
				where TIdentity : IIdentity
		{
			 
				await AggregateStore
				.UpdateAsync<TAggregate, TIdentity>(
					id,
					SourceId.New,
					(a, c) => {
						action(a);
						return Task.FromResult(0);
					},
					CancellationToken.None)
				.ConfigureAwait(false); 			
		} 
		
		public void Dispose()
		{
			Resolver?.Dispose();
		}
	}
}