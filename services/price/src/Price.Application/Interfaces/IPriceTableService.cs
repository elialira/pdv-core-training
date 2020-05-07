using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Application.Interfaces
{
    public interface IPriceTableService
    {
         Task<PriceTableId> Create(CancellationToken cancellationToken, string name, List<ProductPrice> productPrice);
    }
}