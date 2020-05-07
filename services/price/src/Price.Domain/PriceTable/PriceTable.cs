using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using EventFlow.Aggregates.ExecutionResults;
using EventFlow.Exceptions;
using EventFlow.Snapshots;
using EventFlow.Snapshots.Strategies;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.Snapshots;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable
{
  public class PriceTable : SnapshotAggregateRoot<PriceTable, PriceTableId, PriceTableSnapshot>
  {
    private readonly PriceTableState _state = new PriceTableState();

    public PriceTable(PriceTableId priceTableId)
      : base(priceTableId, SnapshotEveryFewVersionsStrategy.With(SnapshotEveryVersion))
      => Register(_state);

    #region Snapshots
    public const int SnapshotEveryVersion = 100;

    protected override Task<PriceTableSnapshot> CreateSnapshotAsync(
      CancellationToken cancellationToken)
    {
      return Task.FromResult(
        new PriceTableSnapshot(
          _state.Name,
          _state.ProductPrices.ToList(),
          _state.ValidityPeriod,        
          Enumerable.Empty<PriceTableSnapshotVersion>())
      );
    }

    protected override Task LoadSnapshotAsync(
      PriceTableSnapshot snapshot,
      ISnapshotMetadata metadata,
      CancellationToken cancellationToken)
    {
      _state.LoadSnapshot(snapshot);

      return Task.FromResult(0);
    }     
    #endregion  

    public IExecutionResult Create(
      string name, 
      List<ProductPrice> productPrices,
      ValidityPeriod validityPeriod)
    {
      if (!IsNew) throw DomainError.With("PriceTable is already created");
      Emit(new PriceTableCreatedEvent(name, productPrices, validityPeriod));
      return ExecutionResult.Success();
    }

    public IExecutionResult AddProductPrice(ProductPrice productPrice)
    {
      Emit(new ProductPriceAddedEvent(productPrice));
      return ExecutionResult.Success();
    } 
  }
}