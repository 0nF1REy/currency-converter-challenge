package br.com.alanryan.currency_converter.app;

import br.com.alanryan.currency_converter.api.ExchangeRateApiClient;
import br.com.alanryan.currency_converter.util.Util;
import br.com.alanryan.currency_converter.model.ExchangeRateResponse;
import br.com.alanryan.currency_converter.service.CurrencyConverterService;
import br.com.alanryan.currency_converter.util.ConversionHistory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        final String apiKey = "27e0e0261fabe51366495de9";
        final String baseCurrency = "USD";

        try {
            ExchangeRateResponse exchangeRateResponse = ExchangeRateApiClient.fetchExchangeRates(apiKey, baseCurrency);
            CurrencyConverterService converterService = new CurrencyConverterService(exchangeRateResponse);
            ConversionHistory history = new ConversionHistory();
            Scanner scanner = new Scanner(System.in);

            List<String> selectedCurrencies = List.of("USD", "BRL", "EUR", "GBP");
            int option;

            do {
                Util.print("\n=== Conversor de Moedas ===");
                Util.print("Base Currency: " + exchangeRateResponse.baseCode());
                Util.print("Taxas disponíveis:");
                selectedCurrencies.forEach(currency -> {
                    Double rate = exchangeRateResponse.conversionRates().get(currency);
                    if (rate != null) System.out.printf("%s -> %.4f%n", currency, rate);
                });

                Util.print("""
                        \nEscolha uma opção:
                        1 - Converter moedas manualmente
                        2 - USD -> BRL
                        3 - BRL -> USD
                        4 - EUR -> USD
                        5 - USD -> EUR
                        6 - GBP -> USD
                        7 - USD -> GBP
                        8 - Ver histórico de conversões
                        0 - Sair
                        """);
                Util.print("Opção: ");
                option = scanner.nextInt();
                scanner.nextLine();

                String fromCurrency = "";
                String toCurrency = "";
                double amount = 0;

                switch (option) {
                    case 1 -> {
                        Util.print("Digite a moeda de origem (ex: USD): ");
                        fromCurrency = scanner.nextLine().toUpperCase();
                        Util.print("Digite a moeda de destino (ex: BRL): ");
                        toCurrency = scanner.nextLine().toUpperCase();
                        Util.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                    }
                    case 2 -> { fromCurrency = "USD"; toCurrency = "BRL"; amount = askAmount(scanner); }
                    case 3 -> { fromCurrency = "BRL"; toCurrency = "USD"; amount = askAmount(scanner); }
                    case 4 -> { fromCurrency = "EUR"; toCurrency = "USD"; amount = askAmount(scanner); }
                    case 5 -> { fromCurrency = "USD"; toCurrency = "EUR"; amount = askAmount(scanner); }
                    case 6 -> { fromCurrency = "GBP"; toCurrency = "USD"; amount = askAmount(scanner); }
                    case 7 -> { fromCurrency = "USD"; toCurrency = "GBP"; amount = askAmount(scanner); }
                    case 8 -> {
                        Util.print("\n=== Histórico de Conversões ===");
                        history.printHistory();
                        continue;
                    }
                    case 0 -> continue;
                    default -> {
                        Util.print("Opção inválida!");
                        continue;
                    }
                }

                if (!fromCurrency.isEmpty() && !toCurrency.isEmpty()) {
                    double result = converterService.convert(fromCurrency, toCurrency, amount);
                    if (result >= 0) {
                        System.out.printf("\n%.2f %s = %.2f %s%n", amount, fromCurrency, result, toCurrency);
                        String timestamp = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        history.addRecord("[%s] %.2f %s -> %.2f %s".formatted(timestamp, amount, fromCurrency, result, toCurrency));
                    } else {
                        Util.print("Erro: uma ou ambas as moedas não estão disponíveis.");
                    }
                }

            } while (option != 0);

            Util.print("Programa encerrado.");
            scanner.close();

        } catch (IOException | InterruptedException e) {
            Util.print("Erro ao obter dados da API: " + e.getMessage());
        }
    }

    private static double askAmount(Scanner scanner) {
        Util.print("Digite o valor a converter: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        return amount;
    }
}
