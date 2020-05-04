using System.Collections.Generic;
using EventFlow.Aggregates;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable
{
  public class PriceTableState : AggregateState<PriceTable, PriceTableId, PriceTableState>,
      IApply<PriceTableCreatedEvent>,
      IApply<ProductPriceAddedEvent>
  {
    private string _name;
    public string Name { get => _name; }

    private readonly List<ProductPrice> _productPrices;
    public IReadOnlyList<ProductPrice> Prices => _productPrices.AsReadOnly();

    public PriceTableState() => _productPrices = new List<ProductPrice>();

    public void Apply(PriceTableCreatedEvent aggregateEvent)
    {
      _name = aggregateEvent.Name;
    }

    public void Apply(ProductPriceAddedEvent aggregateEvent)
    {
      _productPrices.Add(aggregateEvent.ProductPrice);
    }
  }
}