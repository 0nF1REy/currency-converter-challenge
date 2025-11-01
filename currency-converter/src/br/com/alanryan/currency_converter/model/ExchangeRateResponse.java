package br.com.alanryan.currency_converter.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public record ExchangeRateResponse(
        String result,
        String documentation,
        @SerializedName("base_code") String baseCode,
        @SerializedName("conversion_rates") Map<String, Double> conversionRates
) {}
