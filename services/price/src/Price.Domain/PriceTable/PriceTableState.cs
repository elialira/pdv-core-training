using System;
using System.Collections.Generic;
using System.Linq;
using EventFlow.Aggregates;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.Snapshots;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable
{
  public class PriceTableState : AggregateState<PriceTable, PriceTableId, PriceTableState>,
      IApply<PriceTableCreatedEvent>,
      IApply<ProductPriceAddedEvent>
  {
    private string _name;
    public string Name { get => _name; }

    private List<ProductPrice> _productPrices;
    public IReadOnlyList<ProductPrice> ProductPrices => _productPrices.AsReadOnly();

    public IReadOnlyCollection<PriceTableSnapshotVersion> SnapshotVersions 
      { get; private set; } = new PriceTableSnapshotVersion[] { };

    public PriceTableState() 
    {
      _name = String.Empty;
      _productPrices = new List<ProductPrice>();      
    }

    public void LoadSnapshot(PriceTableSnapshot snapshot)
    {
        _name = snapshot.Name;
        _productPrices = snapshot.ProductPrices.ToList();
        SnapshotVersions = snapshot.PreviousVersions;
    }

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