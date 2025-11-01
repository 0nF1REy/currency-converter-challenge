package com.alanryan.currencyconverter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    // Classe para representar o JSON retornado pela API
    public static class ExchangeRateResponse {
        String result;
        String documentation;
        @SerializedName("base_code")
        String baseCode;
        @SerializedName("conversion_rates")
        java.util.Map<String, Double> conversionRates;
    }

    public static void main(String[] args) {

        String apiKey = "27e0e0261fabe51366495de9"; // sua API key
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
            System.out.println("Conversion Rates:");
            exchangeRateResponse.conversionRates.forEach((currency, rate) ->
                    System.out.println(currency + " -> " + rate));

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter dados da API: " + e.getMessage());
        }
    }
}
