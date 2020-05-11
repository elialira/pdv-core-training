using Price.Application.ViewModels;
using System;
using System.Collections.Generic;

namespace Price.Api.tests.Builders
{
  public class PriceTableViewModelBuilder
  {
    public PriceTableIdViewModel Id;
    public string Name;
    public List<ProductPriceViewModel> ProductPrices;
    public ValidityPeriodViewModel ValidityPeriod;

    public static PriceTableViewModelBuilder New()
    {
      return new PriceTableViewModelBuilder()
      {
        Name = $"PriceTable{new Random().Next(1, 9999)}",
        ProductPrices = new List<ProductPriceViewModel>() { ProductPriceViewModelBuilder.New().Build() },
        ValidityPeriod = ValidityPeriodViewModelBuilder.New().Build()
      };
    }

    public PriceTableViewModelBuilder WithId(PriceTableIdViewModel id)
    {
      Id = id;
      return this;
    }

    public PriceTableViewModelBuilder WithProductTypes(List<ProductPriceViewModel> productPrices)
    {
      ProductPrices = productPrices;
      return this;
    }

    public PriceTableViewModelBuilder WithName(string name)
    {
      Name = name;
      return this;
    }

    public PriceTableViewModel Build()
    => new PriceTableViewModel()
    {
      Id = Id,
      Name = Name,
      ProductPrices = ProductPrices,
      ValidityPeriod = ValidityPeriod
    };
  }
}
