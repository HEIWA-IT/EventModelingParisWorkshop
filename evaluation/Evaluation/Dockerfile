#See https://aka.ms/containerfastmode to understand how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-buster-slim AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

VOLUME /tmp/events/evaluation

FROM mcr.microsoft.com/dotnet/core/sdk:3.1-buster AS build
WORKDIR /src
COPY ["Evaluation/Evaluation.csproj", "Evaluation/"]
RUN dotnet restore "Evaluation/Evaluation.csproj"
COPY . .
WORKDIR "/src/Evaluation"
RUN dotnet build "Evaluation.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "Evaluation.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Evaluation.dll"]