using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;
using Price.Infra.ReadModels;

namespace Price.Application.Interfaces
{
	public interface IPriceTableService
	{
		Task<PriceTableId> Create(string name, 
								  List<ProductPrice> productPrice, 
							      CancellationToken cancellationToken);
		Task<IEnumerable<PriceTableReadModel>> GetAll(CancellationToken cancellationToken);
		Task<PriceTableReadModel> GetById(string id, CancellationToken cancellationToken);
		Task<PriceTableId> Update(CancellationToken cancellationToken);
		Task<PriceTableId> Remove(Guid id);
	}
}