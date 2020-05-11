using System;
using System.Collections.Generic;

namespace Price.Application.ViewModels
{
  public class PriceTableViewModel
  {    
    public PriceTableIdViewModel Id { get; set; }
    public string Name { get; set; }
    public List<ProductPriceViewModel> ProductPrices { get; set; }
    public ValidityPeriodViewModel ValidityPeriod { get; set; }
  }
}
