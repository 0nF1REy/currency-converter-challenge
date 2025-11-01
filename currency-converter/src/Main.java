import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

            List<String> selectedCurrencies = List.of("ARS", "BOB", "BRL", "CLP", "COP", "USD");

            Scanner scanner = new Scanner(System.in);
            int option;

            do {
                System.out.println("\n=== Conversor de Moedas ===");
                System.out.println("Base Currency: " + exchangeRateResponse.baseCode);
                System.out.println("Taxas filtradas disponíveis:");
                for (String currency : selectedCurrencies) {
                    Double rate = exchangeRateResponse.conversionRates.get(currency);
                    if (rate != null) {
                        System.out.printf("%s -> %.4f%n", currency, rate);
                    }
                }

                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Converter moedas");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                option = scanner.nextInt();
                scanner.nextLine();

                if (option == 1) {
                    System.out.print("Digite a moeda de origem (ex: USD): ");
                    String fromCurrency = scanner.nextLine().toUpperCase();

                    System.out.print("Digite a moeda de destino (ex: BRL): ");
                    String toCurrency = scanner.nextLine().toUpperCase();

                    System.out.print("Digite o valor a converter: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    double convertedValue = convertCurrency(exchangeRateResponse, fromCurrency, toCurrency, amount);

                    if (convertedValue >= 0) {
                        System.out.printf("\n%.2f %s = %.2f %s%n",
                                amount, fromCurrency, convertedValue, toCurrency);
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

        if (fromRate == null || toRate == null) {
            return -1;
        }

        double valueInUSD = amount / fromRate;
        return valueInUSD * toRate;
    }
}
