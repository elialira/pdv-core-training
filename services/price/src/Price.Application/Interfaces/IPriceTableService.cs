using System.Threading;
using System.Threading.Tasks;
using Price.Domain.PriceTable;

namespace Price.Application.Interfaces
{
    public interface IPriceTableService
    {
         Task<PriceTableId> Create(CancellationToken cancellationToken);
    }
}