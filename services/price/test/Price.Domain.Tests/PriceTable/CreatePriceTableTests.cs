using System;
using Xunit;
using System.Collections.Generic;
using System.Threading.Tasks;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;
using System.Threading;
using EventFlow.Aggregates;
using FluentAssertions;

namespace Price.Domain.Tests.PriceTable
{
  public class CreatePriceTableTests : DomainTestBase
  {
    private List<ProductPrice> _productPrices;
    private string _name = "PriceTable_1";
    private ValidityPeriod _validity = new ValidityPeriod(DateTime.Now.AddDays(-1), DateTime.Now);

    [Fact]
    public async Task AfterCreateShouldHavePriceTableWithName()
    {
      var id = PriceTableId.New;
      var aggregate = new Domain.PriceTable.PriceTable(id);

      Action<IAggregateRoot<PriceTableId>> action;

      action = i => aggregate.Create(_name, _productPrices, _validity);       

      var priceTable = await AggregateStore
        .LoadAsync<Domain.PriceTable.PriceTable, PriceTableId>(
            aggregate.Id,
            CancellationToken.None);

      await UpdateAsync(aggregate.Id, action); 

      priceTable.Name.Should().NotBeNull();
    }
  }
}