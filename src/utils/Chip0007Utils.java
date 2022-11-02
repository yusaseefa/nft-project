package utils;

import java.util.UUID;

public class Chip0007Utils {

    public static String stringOrEmpty(String str) {
        return str != null ? str : "";
    }

    public static int numberOrZero(String number) {
        try {
            int actualNumber = Integer.parseInt(number);
            return actualNumber;
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static String uuidOrNew(String uuid) {
        return uuid != null ? uuid : UUID.randomUUID().toString().toUpperCase();
    }

}
