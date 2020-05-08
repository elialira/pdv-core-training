using System;
using System.Collections.Generic;
using System.Linq;
using EventFlow.Snapshots;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable.Snapshots
{
  [Serializable]
  [SnapshotVersion("PriceTableSnapshot", 1)]
  public class PriceTableSnapshot : ISnapshot
  {
    #region Variables
    public string Name { get; }
    public List<ProductPrice> ProductPrices { get; }
    public IReadOnlyCollection<PriceTableSnapshotVersion> PreviousVersions { get; }
    public ValidityPeriod ValidityPeriod { get; }
    #endregion

    public PriceTableSnapshot(
			String name,
			List<ProductPrice> productPrices,
			ValidityPeriod validityPeriod,
			IEnumerable<PriceTableSnapshotVersion> previousVersions)
    {
      Name = name ?? String.Empty;
      ProductPrices = (productPrices ?? Enumerable.Empty<ProductPrice>()).ToList();
      PreviousVersions = (previousVersions ?? Enumerable.Empty<PriceTableSnapshotVersion>()).ToList();
      ValidityPeriod = validityPeriod;
    }
  }
}
