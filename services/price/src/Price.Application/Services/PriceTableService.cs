using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using AutoMapper;
using EventFlow;
using EventFlow.Queries;
using Price.Application.Interfaces;
using Price.Domain.PriceTable;
using Price.Domain.PriceTable.Commands;
using Price.Infra.ReadModels;
using Price.Domain.PriceTable.ValueObjects;
using Price.Infra.Queries;
using Price.Application.ViewModels;

namespace Price.Application.Services
{
  public class PriceTableService : IPriceTableService
  {
    private readonly ICommandBus _commandBus;
    private readonly IQueryProcessor _queryProcessor;
    private readonly IMapper _mapper;

    public PriceTableService(
      ICommandBus commandBus,
      IQueryProcessor queryProcessor,
      IMapper mapper)
    {
      _commandBus = commandBus;
      _queryProcessor = queryProcessor;      
      _mapper = mapper;
    }

    public async Task<PriceTableIdViewModel> Create(
      PriceTableViewModel viewModel,
      CancellationToken cancellationToken)
    {
      var id = PriceTableId.New;

      await _commandBus
        .PublishAsync(
          new CreatePriceTableCommand(
            id,
            viewModel.Name, 
            _mapper.Map<List<ProductPrice>>(viewModel.ProductPrices), 
            _mapper.Map<ValidityPeriod>(viewModel.ValidityPeriod)
          ),
          cancellationToken)
        .ConfigureAwait(false);

      return _mapper.Map<PriceTableIdViewModel>(id);
    }

    public Task<PriceTableIdViewModel> Remove(Guid id)
      => throw new NotImplementedException();

    public Task<PriceTableViewModel> Update(CancellationToken cancellationToken)
      => throw new NotImplementedException();

    public async Task<PriceTableViewModel> GetById(string id, CancellationToken cancellationToken)
      => _mapper.Map<PriceTableViewModel>(
          await _queryProcessor.ProcessAsync(
            new ReadModelByIdQuery<PriceTableReadModel>(id), 
            cancellationToken));

    public async Task<IEnumerable<PriceTableViewModel>> GetAll(
      CancellationToken cancellationToken)
    {
      var readModel = await _queryProcessor.ProcessAsync(
        new GetAllPriceTablesQuery(), cancellationToken
      );

      return _mapper.Map<IEnumerable<PriceTableViewModel>>(
        (IEnumerable<PriceTableReadModel>)readModel);
    }
  }
}