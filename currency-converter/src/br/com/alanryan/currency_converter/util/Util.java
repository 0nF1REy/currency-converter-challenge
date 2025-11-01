package br.com.alanryan.currency_converter.util;

public class Util {

    public static void print(Object obj) {
        System.out.println(obj);
    }

    // Sobrecarga
    public static void print() {
        System.out.println();
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static void printfn(String format, Object... args) {
        System.out.printf(format + "%n", args);
    }
}
