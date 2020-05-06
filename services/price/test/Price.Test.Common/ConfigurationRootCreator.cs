using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Price.Test.Common
{
  public class ConfigurationRootCreator
  {
    public static IConfiguration Create(IServiceCollection services)
    {
      services.AddOptions();

      var confBuilder = new ConfigurationBuilder();
      confBuilder.AddJsonFile("unittest.json", optional: false);

      var configuration = confBuilder.Build();
      services.AddSingleton((IConfiguration) configuration);

      return configuration;
    }
  }
}
