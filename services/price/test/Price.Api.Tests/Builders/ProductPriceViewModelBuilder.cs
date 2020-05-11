using Price.Application.ViewModels;
using System;

namespace Price.Api.tests.Builders
{
  public class ProductPriceViewModelBuilder
  {
    public string ProductId;
    public decimal Price;
    public static ProductPriceViewModelBuilder New()
    {
      return new ProductPriceViewModelBuilder()
      {
        ProductId = $"ProductPrice{new Random().Next(1, 9999)}",
        Price =new Random().Next(1, 9999)
      };
    }

    public ProductPriceViewModel Build()
    => new ProductPriceViewModel()
    {
      ProductId = ProductId,
      Price = Price
    };
  }
}
