using System;
using Xunit;
using System.Collections.Generic;
using System.Threading.Tasks;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;
using System.Threading;
using EventFlow.Aggregates;
using FluentAssertions;
using EventFlow.Exceptions;

namespace Price.Domain.Tests.PriceTable
{
  public class CreatePriceTableTests : DomainTestBase
  {
    private List<ProductPrice> _productPrices;
    private string _name = "PriceTable_1";
    private ValidityPeriod _validity = new ValidityPeriod(DateTime.Now, DateTime.Now.AddDays(1));
    private ValidityPeriod _invalidPeriod = new ValidityPeriod(DateTime.Now, DateTime.Now.AddDays(-1));
    [Fact]
    public async Task AfterCreateShouldHavePriceTableWithName()
    {
      var id = PriceTableId.New;
      var aggregate = new Domain.PriceTable.PriceTable(id);

      Action<Domain.PriceTable.PriceTable> action;

      action = i => aggregate.Create(_name, _productPrices, _validity);

      await UpdateAsync(aggregate.Id, action);

      var priceTable = await AggregateStore
        .LoadAsync<Domain.PriceTable.PriceTable, PriceTableId>(
            aggregate.Id,
            CancellationToken.None);

      priceTable.Name.Should().NotBeNull();
    }

    [Fact]
    public void WithInvalidPeriodShouldNotCreate()
    {
      var id = PriceTableId.New;
      var aggregate = new Domain.PriceTable.PriceTable(id);

      Action<Domain.PriceTable.PriceTable> action;

      action = i => aggregate.Create(_name, _productPrices, _invalidPeriod);

      Assert.Throws<AggregateException>(() => UpdateAsync(aggregate.Id, action).Wait());
      //Assert.Throws<DomainError>(() => UpdateAsync(aggregate.Id, action).Wait()); 
    }
  }
}
