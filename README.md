# Currency Converter

Aplicação em Java que consulta taxas de câmbio em uma API pública e fornece um conversor de moedas via linha de comando.

Este repositório contém a implementação de um conversor de moedas (CLI) que busca as taxas em tempo real através da API `exchangerate-api.com` e permite realizar conversões entre diferentes moedas, além de manter um histórico de conversões durante a execução.

## Principais características

- Busca de taxas de câmbio em tempo real usando `HttpClient` (Java 11+).
- Conversões manuais e atalhos para pares comuns (USD/BRL, USD/EUR, USD/GBP, etc.).
- Histórico de conversões em memória durante a execução.

## Requisitos

- Java 11 ou superior (o código usa `java.net.http.HttpClient`).
- Dependência externa: Gson (já disponível na pasta `dependencies/gson/gson-2.13.2.jar`).
- Conexão com internet para consultar a API de taxas de câmbio.

## Arquivos do projeto

- `Main.java` — ponto de entrada (CLI).
- `ExchangeRateApiClient.java` — cliente _HTTP_ que busca as taxas na API e desserializa para **ExchangeRateResponse** usando _Gson_.
- `CurrencyConverterService.java` — lógica de conversão entre moedas, usando as taxas obtidas.
- `ExchangeRateResponse.java` — modelo (record) que mapeia a resposta JSON da API.
- `ConversionHistory.java` — armazenamento em memória do histórico de conversões.
- `Util.java` — funções utilitárias para impressão formatada.

## Como compilar e executar

**1.** Entre no diretório do projeto:

    ```bash
    cd currency-converter
    ```

**2.** Compile os arquivos Java (exemplo usando `find` para coletar fontes):

    ```bash
    mkdir -p out
    javac -d out -cp dependencies/gson/gson-2.13.2.jar $(find src -name "*.java")
    ```

**3.** Execute a aplicação:

    ```bash
    java -cp "out:dependencies/gson/gson-2.13.2.jar" br.com.alanryan.currency_converter.app.Main
    ```

**Observações:**

- O projeto não possui um build system (Maven/Gradle) configurado — as instruções acima usam `javac`/`java` diretamente.
- Se preferir, crie um projeto Maven/Gradle e adicione `com.google.code.gson:gson:2.13.2` como dependência.

## Chave de API

No código atual, a chave da API está codificada em `Main.java` na variável `apiKey`:

```java
final String apiKey = "27e0e0261fabe51366495de9";
```

Essa chave pode ser uma chave de exemplo. Para produção ou uso próprio, substitua por sua própria chave ou refatore o código para ler a chave de uma variável de ambiente ou arquivo de configuração.

## Exemplo de uso

Ao executar o `Main`, você verá um menu interativo com opções para:

- Converter manualmente entre duas moedas (opção 1).
- Atalhos para conversões comuns (USD ↔ BRL, USD ↔ EUR, USD ↔ GBP).
- Visualizar o histórico de conversões (opção 8).

O programa imprime o resultado e mantém o histórico apenas durante a execução.
