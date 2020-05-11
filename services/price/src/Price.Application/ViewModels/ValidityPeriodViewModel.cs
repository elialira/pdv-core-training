using System;
using System.Collections.Generic;

namespace Price.Application.ViewModels
{
  public class ValidityPeriodViewModel
  {
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public bool isActive { get; private set; }
  }
}
