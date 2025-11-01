package br.com.alanryan.currency_converter.util;

import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {

    private final List<String> records = new ArrayList<>();

    public void addRecord(String record) {
        records.add(record);
    }

    public void printHistory() {
        if (records.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            records.forEach(System.out::println);
        }
    }
}
