package me.kudashevigor;

import me.kudashevigor.exceptions.IllegalInputException;

import java.util.TreeMap;

public enum RomanNumerals {

    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10);

    private final int arabicValue;

    RomanNumerals(int romanValue) {
        this.arabicValue = romanValue;
    }

    public static String intToStringRomanNumerals(int value) {
        TreeMap<Integer, String> romanStringValues = new TreeMap<>();
        romanStringValues.put(100, "C");
        romanStringValues.put(90, "XC");
        romanStringValues.put(50, "L");
        romanStringValues.put(40, "XL");
        romanStringValues.put(10, "X");
        romanStringValues.put(9, "IX");
        romanStringValues.put(5, "V");
        romanStringValues.put(4, "IV");
        romanStringValues.put(1, "I");
        int i = romanStringValues.floorKey(value);
        if (value == i) {
            return romanStringValues.get(value);
        }
        return romanStringValues.get(i) + intToStringRomanNumerals(value - i);
    }

    public static RomanNumerals parse(String stringValue) throws IllegalInputException {
        for (RomanNumerals rn : values()) {
            if (stringValue.equals(rn.toString())) {
                return rn;
            }
        }
        throw new IllegalInputException(stringValue);
    }

    public int getArabicValue() {
        return arabicValue;
    }

}
