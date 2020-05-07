using System;
using System.Collections.Generic;

namespace Price.Application.ViewModels
{
    public class PriceTableViewModel
    {
        public string Name { get; set; }
        public List<ProductPriceViewModel> ProductPrices { get; set; }
    }
}
