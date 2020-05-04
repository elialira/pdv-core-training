using EventFlow.Aggregates;
using EventFlow.Exceptions;
using Price.Domain.PriceTable.Events;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Domain.PriceTable
{
  public class PriceTable : AggregateRoot<PriceTable, PriceTableId>
  {
    private readonly PriceTableState _state = new PriceTableState();

    public PriceTable(PriceTableId priceTableId) : base(priceTableId) 
      => Register(_state);      

    public void Create(string name) 
    {
      if (!IsNew) throw DomainError.With("PriceTable is already created");      
      Emit(new PriceTableCreatedEvent(name));
    }

    public void AddProductPrice(ProductPrice productPrice) 
    {
      Emit(new ProductPriceAddedEvent(productPrice));
    }
  }
}