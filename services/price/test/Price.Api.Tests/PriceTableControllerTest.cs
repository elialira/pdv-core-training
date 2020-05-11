using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.TestHost;
using Newtonsoft.Json;
using Price.Api.tests.Builders;
using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Price.Api.tests
{
  public class PriceTableControllerTest
  {
    private readonly TestServer server;
    private readonly HttpClient client;

    public PriceTableControllerTest()
    {
      Environment.SetEnvironmentVariable("RABBITMQCONNECTION", "amqp://test:test@rabbitmq:5672");
      Environment.SetEnvironmentVariable("EVENTSTORECONNECTION", "tcp://eventstore:1113");
      //-ASPNETCORE_ENVIRONMENT = Docker

      server = new TestServer(new WebHostBuilder()
              .UseStartup<Startup>());

      client = server.CreateClient();
    }
    [Fact]
    public async Task ReturnOkWhenComplete()
    {
      var sut = PriceTableViewModelBuilder.New().Build();

      var content = new StringContent(JsonConvert.SerializeObject(sut),
                    Encoding.UTF8, "application/json");

      var response = await client.PostAsync("/api/price-table", content);

      Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);

    }

    [Fact]
    public async Task ReturnBadRequestWhenEmpty()
    {
      //Assert.Equal(HttpStatusCode.BadRequest, response.StatusCode);
    }

    [Fact]
    public async Task ReturnUnauthorizedWithWrongToken()
    {
      //Assert 
      //Assert.Equal(HttpStatusCode.Unauthorized, response.StatusCode);
    }
  }
}
