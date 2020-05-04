using EventFlow.Core;
using EventFlow.ValueObjects;
using Newtonsoft.Json;

namespace Price.Domain.PriceTable
{
    [JsonConverter(typeof(SingleValueObjectConverter))]
    public class PriceTableId : Identity<PriceTableId>
    {
        public PriceTableId(string value) : base(value) { }        
    }
}