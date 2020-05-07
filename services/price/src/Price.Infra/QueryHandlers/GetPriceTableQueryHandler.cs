using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using EventFlow.Queries;
using EventFlow.ReadStores.InMemory;
using Price.Domain.PriceTable;
using Price.Infra.Queries;
using Price.Infra.ReadModels;

namespace Price.Infra.QueryHandlers
{
  public class GetPriceTableQueryHandler :
    IQueryHandler<GetPriceTableQuery,
    IReadOnlyCollection<PriceTableReadModel>>
  {
    private readonly IInMemoryReadStore<PriceTableReadModel> _readStore;

    public GetPriceTableQueryHandler(
        InMemoryReadStore<PriceTableReadModel> readStore)
      => _readStore = readStore;

    public async Task<IReadOnlyCollection<PriceTableReadModel>> ExecuteQueryAsync(
      GetPriceTableQuery query,
      CancellationToken cancellationToken)
    {
      var priceTableIds = new HashSet<PriceTableId>(query.PriceTableIds);
      var priceTableReadModels = await _readStore
        .FindAsync(rm => priceTableIds.Contains(rm.Id), cancellationToken)
        .ConfigureAwait(false);

      return priceTableReadModels.ToList();
    }
  }
}