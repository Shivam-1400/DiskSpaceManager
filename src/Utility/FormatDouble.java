package Utility;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatDouble {

    public static void main(String[] args) {
        double usedSpace = 1234567.890123; // Replace this with your actual used space value
        double freeSpace = 9876543.210987; // Replace this with your actual free space value

        double formattedUsedSpace = formatDouble(usedSpace);
        double formattedFreeSpace = formatDouble(freeSpace);

        System.out.println("Formatted used space: " + formattedUsedSpace);
        System.out.println("Formatted free space: " + formattedFreeSpace);

        // The original double values remain unchanged
        System.out.println("Original used space: " + usedSpace);
        System.out.println("Original free space: " + freeSpace);
    }

    public static double formatDouble(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String formattedNumber = numberFormat.format(number);
        return Double.parseDouble(formattedNumber);
    }
}
 