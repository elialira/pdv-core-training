using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Price.Domain.PriceTable;

namespace Price.Application.Interfaces
{
	public interface IPriceTableService
	{
		Task<PriceTableId> Create(CancellationToken cancellationToken);
		Task<IEnumerable<PriceTable>> GetAll(CancellationToken cancellationToken);
		Task<PriceTable> GetById(Guid id, CancellationToken cancellationToken);
		Task<PriceTableId> Update(CancellationToken cancellationToken);
		Task<PriceTableId> Remove(Guid id);
	}
}