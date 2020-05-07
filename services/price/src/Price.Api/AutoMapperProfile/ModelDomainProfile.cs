using AutoMapper;
using Price.Application.ViewModels;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Api.AutoMapperProfile
{
    public class ModelDomainProfile : Profile
    {
        public ModelDomainProfile()
        {
            CreateMap<ProductPriceViewModel, ProductPrice>().ReverseMap();
        }
    }
}
