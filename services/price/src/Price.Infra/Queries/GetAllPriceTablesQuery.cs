using System.Collections.Generic;
using EventFlow.Queries;
using Price.Infra.ReadModels;

namespace Price.Infra.Queries
{
	public class GetAllPriceTablesQuery : 
		IQuery<IReadOnlyCollection<PriceTableReadModel>> { }
}