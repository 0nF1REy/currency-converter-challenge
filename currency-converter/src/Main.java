import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static class ExchangeRateResponse {
        String result;
        String documentation;
        @SerializedName("base_code")
        String baseCode;
        @SerializedName("conversion_rates")
        Map<String, Double> conversionRates;
    }

    public static void main(String[] args) {

        String apiKey = "27e0e0261fabe51366495de9";
        String baseCurrency = "USD";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            Gson gson = new Gson();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(jsonResponse, ExchangeRateResponse.class);

            List<String> selectedCurrencies = new ArrayList<>(List.of(
                    "USD", "BRL", "EUR", "GBP"
            ));

            Scanner scanner = new Scanner(System.in);
            List<String> conversionHistory = new ArrayList<>();
            int option;

            do {
                System.out.println("\n=== Conversor de Moedas ===");
                System.out.println("Base Currency: " + exchangeRateResponse.baseCode);
                System.out.println("Taxas disponíveis:");
                for (String currency : selectedCurrencies) {
                    Double rate = exchangeRateResponse.conversionRates.get(currency);
                    if (rate != null) {
                        System.out.printf("%s -> %.4f%n", currency, rate);
                    }
                }

                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Converter moedas manualmente");
                System.out.println("2 - Conversão rápida: USD -> BRL");
                System.out.println("3 - Conversão rápida: BRL -> USD");
                System.out.println("4 - Conversão rápida: EUR -> USD");
                System.out.println("5 - Conversão rápida: USD -> EUR");
                System.out.println("6 - Conversão rápida: GBP -> USD");
                System.out.println("7 - Conversão rápida: USD -> GBP");
                System.out.println("8 - Ver histórico de conversões");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                option = scanner.nextInt();
                scanner.nextLine();

                String fromCurrency = "";
                String toCurrency = "";
                double amount = 0;

                switch (option) {
                    case 1:
                        System.out.print("Digite a moeda de origem (ex: USD): ");
                        fromCurrency = scanner.nextLine().toUpperCase();
                        System.out.print("Digite a moeda de destino (ex: BRL): ");
                        toCurrency = scanner.nextLine().toUpperCase();
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 2:
                        fromCurrency = "USD"; toCurrency = "BRL";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 3:
                        fromCurrency = "BRL"; toCurrency = "USD";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 4:
                        fromCurrency = "EUR"; toCurrency = "USD";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 5:
                        fromCurrency = "USD"; toCurrency = "EUR";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 6:
                        fromCurrency = "GBP"; toCurrency = "USD";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 7:
                        fromCurrency = "USD"; toCurrency = "GBP";
                        System.out.print("Digite o valor a converter: ");
                        amount = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    case 8:
                        System.out.println("\n=== Histórico de Conversões ===");
                        if (conversionHistory.isEmpty()) {
                            System.out.println("Nenhuma conversão realizada ainda.");
                        } else {
                            conversionHistory.forEach(System.out::println);
                        }
                        continue;
                    case 0:
                        continue;
                    default:
                        System.out.println("Opção inválida!");
                        continue;
                }

                if (!fromCurrency.isEmpty() && !toCurrency.isEmpty()) {
                    double convertedValue = convertCurrency(exchangeRateResponse, fromCurrency, toCurrency, amount);
                    if (convertedValue >= 0) {
                        System.out.printf("\n%.2f %s = %.2f %s%n",
                                amount, fromCurrency, convertedValue, toCurrency);
                        String timestamp = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        conversionHistory.add(String.format("[%s] %.2f %s -> %.2f %s",
                                timestamp, amount, fromCurrency, convertedValue, toCurrency));
                    } else {
                        System.out.println("Erro: uma ou ambas as moedas não estão disponíveis.");
                    }
                }

            } while (option != 0);

            System.out.println("Programa encerrado.");
            scanner.close();

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter dados da API: " + e.getMessage());
        }
    }

    public static double convertCurrency(ExchangeRateResponse data, String from, String to, double amount) {
        Double fromRate = data.conversionRates.get(from);
        Double toRate = data.conversionRates.get(to);
        if (fromRate == null || toRate == null) return -1;
        double valueInUSD = amount / fromRate;
        return valueInUSD * toRate;
    }
}
