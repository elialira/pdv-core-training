using AutoMapper;
using Price.Application.ViewModels;
using Price.Domain.PriceTable.ValueObjects;

namespace Price.Application.AutoMapperProfile
{
    public class ModelDomainProfile : Profile
    {
        public ModelDomainProfile()
        {
            CreateMap<ProductPriceViewModel, ProductPrice>().ReverseMap();
        }
    }
}
