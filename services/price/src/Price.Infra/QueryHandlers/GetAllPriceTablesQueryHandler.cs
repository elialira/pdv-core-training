using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using EventFlow.Queries;
using EventFlow.ReadStores.InMemory;
using Price.Infra.ReadModels;
using Price.Infra.Queries;

namespace Price.Infra.QueryHandlers
{
  public class GetAllPriceTablesQueryHandler :
    IQueryHandler<GetAllPriceTablesQuery,IReadOnlyCollection<PriceTableReadModel>>
  {
    private readonly IInMemoryReadStore<PriceTableReadModel> _readStore;

    public GetAllPriceTablesQueryHandler(
        IInMemoryReadStore<PriceTableReadModel> readStore)
      => _readStore = readStore;

    public async Task<IReadOnlyCollection<PriceTableReadModel>> ExecuteQueryAsync(
      GetAllPriceTablesQuery query,
      CancellationToken cancellationToken)
    {
      var priceTableReadModels = await _readStore.FindAsync(rm => true, cancellationToken);
      return priceTableReadModels.ToList();
    }
  }
}