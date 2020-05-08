using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Price.Application.ViewModels;

namespace Price.Application.Interfaces
{
	public interface IPriceTableService
	{
		Task<PriceTableIdViewModel> Create(PriceTableViewModel viewModel, CancellationToken cancellationToken);
		Task<IEnumerable<PriceTableViewModel>> GetAll(CancellationToken cancellationToken);
		Task<PriceTableViewModel> GetById(string id, CancellationToken cancellationToken);
		Task<PriceTableViewModel> Update(CancellationToken cancellationToken);
		Task<PriceTableIdViewModel> Remove(Guid id);
	}
}