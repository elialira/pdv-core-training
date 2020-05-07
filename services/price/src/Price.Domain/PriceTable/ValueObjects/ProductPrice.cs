using System;
using EventFlow.ValueObjects;

namespace Price.Domain.PriceTable.ValueObjects
{
  public class ProductPrice : ValueObject
  {
    public ProductPrice(string productId, decimal price)
    {
      this.ProductId = productId;
      this.Price = price;
    }
    
    public string ProductId { get; private set; }
    public decimal Price { get; private set; }
  }
}