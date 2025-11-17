<h1 align="center">
  Conversor de Moedas
</h1>

<div align="center">

![Maintenance](https://img.shields.io/maintenance/yes/2025?style=for-the-badge)
![License MIT](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/status-completed-brightgreen?style=for-the-badge)

<img src="./readme_images/badge-main-one.png" height="300" alt="Badge Principal do Oracle Next Education">
<img src="./readme_images/badge-converter-one.png" height="300" alt="Badge conversor do Oracle Next Education">

</div>

## 📖 Descrição

Aplicação em Java que consulta taxas de câmbio em uma API pública e fornece um conversor de moedas via linha de comando.

Este repositório contém a implementação de um conversor de moedas (CLI) que busca as taxas em tempo real através da API `exchangerate-api.com` e permite realizar conversões entre diferentes moedas, além de manter um histórico de conversões durante a execução.

## ✨ Principais características

- Busca de taxas de câmbio em tempo real usando `HttpClient` (Java 11+).
- Conversões manuais e atalhos para pares comuns (USD/BRL, USD/EUR, USD/GBP, etc.).
- Histórico de conversões em memória durante a execução.

## 🛠️ Requisitos

- _Java 11_ ou superior (o código usa `java.net.http.HttpClient`).
- Dependência externa: Gson (já disponível na pasta `dependencies/gson/gson-2.13.2.jar`).
- Conexão com internet para consultar a API de taxas de câmbio.

## 📁 Arquivos do projeto

- `Main.java` — ponto de entrada (CLI).
- `ExchangeRateApiClient.java` — cliente _HTTP_ que busca as taxas na API e desserializa para **ExchangeRateResponse** usando _Gson_.
- `CurrencyConverterService.java` — lógica de conversão entre moedas, usando as taxas obtidas.
- `ExchangeRateResponse.java` — modelo (record) que mapeia a resposta JSON da API.
- `ConversionHistory.java` — armazenamento em memória do histórico de conversões.
- `Util.java` — funções utilitárias para impressão formatada.

## 🚀 Como compilar e executar

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

## 🔑 Chave de API

A chave da API está codificada em `Main.java` na variável `apiKey`:

```java
final String apiKey = "27e0e0261fabe51366495de9";
```

## ▶️ Exemplo de uso

Ao executar o `Main`, você verá um menu interativo com opções para:

- Converter manualmente entre duas moedas (opção 1).
- Atalhos para conversões comuns (USD ↔ BRL, USD ↔ EUR, USD ↔ GBP).
- Visualizar o histórico de conversões (opção 8).

O programa imprime o resultado e mantém o histórico apenas durante a execução.

---

## 👤 Sobre o Desenvolvedor

<div align="center">

<table>
  <tr>
    <td align="center">
        <br>
        <a href="https://github.com/0nF1REy" target="_blank">
          <img src="./readme_images/alan-ryan.jpg" height="160" alt="Foto de Alan Ryan">
        </a>
        </p>
        <a href="https://github.com/0nF1REy" target="_blank">
          <strong>Alan Ryan</strong>
        </a>
        </p>
        ☕ Peopleware | Tech Enthusiast | Code Slinger ☕
        <br>
        Apaixonado por código limpo, arquitetura escalável e experiências digitais envolventes
        </p>
          Conecte-se comigo:
        </p>
        <a href="https://www.linkedin.com/in/alan-ryan-b115ba228" target="_blank">
          <img src="https://img.shields.io/badge/LinkedIn-Alan_Ryan-0077B5?style=flat&logo=linkedin" alt="LinkedIn">
        </a>
        <a href="https://gitlab.com/alanryan619" target="_blank">
          <img src="https://img.shields.io/badge/GitLab-@0nF1REy-FCA121?style=flat&logo=gitlab" alt="GitLab">
        </a>
        <a href="mailto:alanryan619@gmail.com" target="_blank">
          <img src="https://img.shields.io/badge/Email-alanryan619@gmail.com-D14836?style=flat&logo=gmail" alt="Email">
        </a>
        </p>
    </td>
  </tr>
</table>

</div>

---

## 📜 Licença

Este projeto está sob a **licença MIT**. Consulte o arquivo **[LICENSE](LICENSE)** para obter mais detalhes.

> ℹ️ **Aviso de Licença:** © 2025 Alan Ryan da Silva Domingues. Este projeto está licenciado sob os termos da licença MIT. Isso significa que você pode usá-lo, copiá-lo, modificá-lo e distribuí-lo com liberdade, desde que mantenha os avisos de copyright.

⭐ Se este repositório foi útil para você, considere dar uma estrela!
