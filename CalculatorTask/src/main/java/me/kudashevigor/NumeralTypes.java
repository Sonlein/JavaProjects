package me.kudashevigor;

import me.kudashevigor.exceptions.IllegalInputException;

public enum NumeralTypes {

    ARABIC,
    ROMAN;

    public static NumeralTypes getType(String stringValue) throws IllegalInputException {
        try {
            int value = Integer.parseInt(stringValue);
            if (value >= 1 && value <= 10) {
                return ARABIC;
            }
            throw new IllegalInputException(stringValue);
        } catch (NumberFormatException ignored) {
            RomanNumerals value = RomanNumerals.parse(stringValue);
            if (value.getArabicValue() >= 1 && value.getArabicValue() <= 10) {
                return ROMAN;
            }
            throw new IllegalInputException(stringValue);
        }
    }

    public static int getIntValue(String stringValue) throws IllegalInputException {
        NumeralTypes nt = getType(stringValue);
        if (nt == ROMAN) {
            return RomanNumerals.parse(stringValue).getArabicValue();
        }
        return Integer.parseInt(stringValue);
    }

}
