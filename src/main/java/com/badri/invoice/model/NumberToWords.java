package com.badri.invoice.model;


public class NumberToWords {

    private static final String[] units = {
            "", "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen",
            "Fifteen", "Sixteen", "Seventeen", "Eighteen",
            "Nineteen"
    };

    private static final String[] tens = {
            "", "", "Twenty", "Thirty", "Forty",
            "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    public static String convert(double amount) {
        int number = (int) amount;

        if (number == 0) {
            return "Zero";
        }

        return convertToWords(number).trim();
    }

    private static String convertToWords(int number) {

        if (number < 20) {
            return units[number];
        }

        if (number < 100) {
            return tens[number / 10] + " " + units[number % 10];
        }

        if (number < 1000) {
            return units[number / 100] + " Hundred " + convertToWords(number % 100);
        }

        if (number < 100000) {
            return convertToWords(number / 1000) + " Thousand " + convertToWords(number % 1000);
        }

        if (number < 10000000) {
            return convertToWords(number / 100000) + " Lakh " + convertToWords(number % 100000);
        }

        return convertToWords(number / 10000000) + " Crore " + convertToWords(number % 10000000);
    }
}
