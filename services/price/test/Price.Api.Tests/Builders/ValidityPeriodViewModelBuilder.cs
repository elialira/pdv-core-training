using Price.Application.ViewModels;
using System;

namespace Price.Api.tests.Builders
{
  public class ValidityPeriodViewModelBuilder
  {
    public DateTime StartDate;
    public DateTime EndDate;
    //public bool isActive { get;  set; }
    public static ValidityPeriodViewModelBuilder New()
    {
      return new ValidityPeriodViewModelBuilder()
      {
        StartDate = DateTime.Now,
        EndDate = DateTime.Now.AddDays(1),
        //isActive = true
      };
    }

    public ValidityPeriodViewModel Build()
     => new ValidityPeriodViewModel()
     {
       StartDate = StartDate,
       EndDate = EndDate
     };
  }
}
