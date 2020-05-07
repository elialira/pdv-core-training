using System.Collections.Generic;
using System.Linq;
using EventFlow.Queries;
using Price.Domain.PriceTable;
using Price.Infra.ReadModels;

namespace Price.Infra.Queries
{
	public class GetPriceTableQuery : 
		IQuery<IReadOnlyCollection<PriceTableReadModel>>
	{
		public GetPriceTableQuery() { }
		public GetPriceTableQuery(IEnumerable<PriceTableId> priceTableIds)
			=> PriceTableIds = priceTableIds.ToList();
			
		public IReadOnlyCollection<PriceTableId> PriceTableIds { get; }
	}
}