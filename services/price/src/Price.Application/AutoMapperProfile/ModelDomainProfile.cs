using AutoMapper;
using Price.Application.ViewModels;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.ValueObjects;
using Price.Infra.ReadModels;

namespace Price.Application.AutoMapperProfile
{
    public class ModelDomainProfile : Profile
    {
        public ModelDomainProfile()
        {
            CreateMap<PriceTableIdViewModel, PriceTableId>().ReverseMap();
            CreateMap<PriceTableViewModel, PriceTableReadModel>().ReverseMap();
            CreateMap<ProductPriceViewModel, ProductPrice>().ReverseMap();
            CreateMap<ValidityPeriodViewModel, ValidityPeriod>().ReverseMap();
        }
    }
}
