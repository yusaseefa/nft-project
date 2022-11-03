package utils;

import model.Chip007;
import model.RowValue;

import java.util.Arrays;
import java.util.List;
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
        return uuid != null ? uuid : UUID.randomUUID().toString();
    }

    public static void setAttributes(Chip007 chip007, RowValue rowValue) {

        String attributeRow = rowValue.getAttributes();
        List<String> attributes = Arrays.asList(attributeRow.split(";"));

        for (String attribute : attributes) {
            String[] attributeElements = attribute.split(":");
            String traitType = null;
            String traitValue = null;

            int arrLen = attributeElements.length;

            if (arrLen > 1) {
                traitType = attributeElements[0].trim();
                traitValue = attributeElements[1].trim();
            }
            else if (arrLen == 1) {
                traitType = attributeElements[0].trim();
            }
            else {
                traitType = "Unknown";
                traitValue = "Unknown";
            }

            Chip007.Attribute currentAttribute = new Chip007.Attribute();
            currentAttribute.setTraitType(traitType);
            currentAttribute.setValue(stringOrEmpty(traitValue));
            chip007.getAttributes().add(currentAttribute);
        }

    }

}
