using System;
using Xunit;
using System.Collections.Generic;
using System.Threading.Tasks;
using EventFlow.Aggregates.ExecutionResults;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.Tests.PriceTable
{
  public class CreatePriceTableTests : DomainTestBase
  {
		private List<ProductPrice> _productPrices;
		private string _name = "PriceTable_1";

		// [Fact]
		// public async Task AfterCreateShouldHavePriceTableWithName()
		// {
		// 		IExecutionResult Create(PriceTable pt) => pt.Create(_name, _productPrices);

		// 		await UpdateAsync(PriceTableId, (Action<PriceTable>) Create);
				
		// 		var priceTable = await AggregateStore
		// 			.LoadAsync<PriceTable,PriceTableId>(
		// 				PriceTableId, 
		// 				CancellationToken.None);
				
		// 		priceTable.Name.Should().NotBeEmpty();
		// }
  }
}