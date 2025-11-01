package com.alanryan.currencyconverter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {

    // Classe para representar o JSON retornado pela API
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

            System.out.println("Base Currency: " + exchangeRateResponse.baseCode);
            System.out.println("Filtered Conversion Rates:");

            // Lista das moedas que queremos filtrar
            List<String> selectedCurrencies = List.of("ARS", "BOB", "BRL", "CLP", "COP", "USD");

            // Filtra e exibe apenas as moedas selecionadas
            selectedCurrencies.forEach(currency -> {
                Double rate = exchangeRateResponse.conversionRates.get(currency);
                if (rate != null) {
                    System.out.println(currency + " -> " + rate);
                } else {
                    System.out.println(currency + " -> (not available)");
                }
            });

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter dados da API: " + e.getMessage());
        }
    }
}
