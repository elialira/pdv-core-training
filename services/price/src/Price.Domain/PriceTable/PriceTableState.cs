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
    
    private ValidityPeriod _validityPeriod;
    public ValidityPeriod ValidityPeriod { get => _validityPeriod; }

    private List<ProductPrice> _productPrices;
    public IReadOnlyList<ProductPrice> ProductPrices 
      => _productPrices.AsReadOnly();
    
    private IEnumerable<PriceTableSnapshotVersion> _snapshotVersions = 
      new PriceTableSnapshotVersion[] { };
    public IReadOnlyCollection<PriceTableSnapshotVersion> SnapshotVersions 
      => _snapshotVersions.ToList().AsReadOnly();

    public PriceTableState() 
    {
      _name = String.Empty;
      _productPrices = new List<ProductPrice>();      
      _validityPeriod = new ValidityPeriod();
    }

    public void LoadSnapshot(PriceTableSnapshot snapshot)
    {
        _name = snapshot.Name;
        _productPrices = snapshot.ProductPrices.ToList();
        _validityPeriod = snapshot.ValidityPeriod;
        _snapshotVersions = snapshot.PreviousVersions;
    }

    public void Apply(PriceTableCreatedEvent aggregateEvent)
    {
      _name = aggregateEvent.Name;
      _productPrices = aggregateEvent.ProductPrices;
      _validityPeriod = aggregateEvent.ValidityPeriod;
    }

    public void Apply(ProductPriceAddedEvent aggregateEvent)
    {
      _productPrices.Add(aggregateEvent.ProductPrice);
    }
  }
}