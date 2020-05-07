using System;
using System.Collections.Generic;

namespace Price.Api.Model
{
    public class PriceTableViewModel
    { 
        public string Name { get; set; } 
        public List<ProductPriceViewModel> ProductPrices { get; set; }
    }
}
