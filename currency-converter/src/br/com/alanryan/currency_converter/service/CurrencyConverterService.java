package br.com.alanryan.currency_converter.service;

import br.com.alanryan.currency_converter.model.ExchangeRateResponse;

public class CurrencyConverterService {

    private final ExchangeRateResponse data;

    public CurrencyConverterService(ExchangeRateResponse data) {
        this.data = data;
    }

    public double convert(String from, String to, double amount) {
        Double fromRate = data.conversionRates().get(from);
        Double toRate = data.conversionRates().get(to);
        if (fromRate == null || toRate == null) return -1;
        double valueInUSD = amount / fromRate;
        return valueInUSD * toRate;
    }
}
