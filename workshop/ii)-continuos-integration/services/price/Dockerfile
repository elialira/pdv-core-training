FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-buster-slim AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

#####################################################
###  Stage: Dependencies                          ###
#####################################################
FROM mcr.microsoft.com/dotnet/core/sdk:3.1-buster AS build
WORKDIR /src
COPY /src .
RUN dotnet restore "Price.Api/Price.Api.csproj"

#####################################################
###  Stage: Compile                               ###
#####################################################

WORKDIR "/src/Price.Api"
RUN dotnet build "Price.Api.csproj" -c Docker -o /app/build 

#####################################################
###  Stage(Optional): Run Unit Tests              ###
#####################################################

FROM build AS test
ARG SKIPTESTS=true
WORKDIR /test
COPY /test .
RUN if [ "$SKIPTESTS" = "false" ]; \ 
    then ls **/*Tests.csproj | xargs -L1 dotnet test --logger:trx --collect:"XPlat Code Coverage" /p:CollectCoverage=true; \
    fi

#####################################################
###  Stage: publish                               ###
#####################################################

FROM build AS publish
RUN dotnet publish "Price.Api.csproj" -c Docker -o /app/publish

#####################################################
###  Stage: Run Image                             ###
#####################################################
FROM base AS final
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Price.Api.dll"]