package me.kudashevigor;

import me.kudashevigor.exceptions.IllegalInputException;
import me.kudashevigor.exceptions.IncompatibleNumeralTypesException;

import java.util.function.BinaryOperator;

public enum OperationTypes {

    ADDITION("+", Integer::sum),
    SUBTRACTION("-", (a, b) -> a - b),
    MULTIPLICATION("*", (a, b) -> a * b),
    DIVISION("/", (a, b) -> a / b);

    private final String value;
    private final BinaryOperator<Integer> operator;

    OperationTypes(String value, BinaryOperator<Integer> operator) {
        this.value = value;
        this.operator = operator;
    }

    public static String[] getStringValues() {
        String[] values = new String[values().length];
        for (int i = 0; i < values.length; i++) {
            values[i] = values()[i].value;
        }
        return values;
    }

    public static String calculate(String stringValue) throws IllegalInputException, IncompatibleNumeralTypesException {
        stringValue = stringValue.replaceAll(" ","");
        for (OperationTypes ot : values()) {
            if (stringValue.contains(ot.value)) {
                String regex = String.format("\\%s", ot.value);
                String[] splitValue = stringValue.split(regex);
                if (splitValue.length != 2) {
                    throw new IllegalInputException(stringValue);
                }
                NumeralTypes nt1 = NumeralTypes.getType(splitValue[0]);
                NumeralTypes nt2 = NumeralTypes.getType(splitValue[1]);
                if (nt1 != nt2) {
                    throw new IncompatibleNumeralTypesException(splitValue[0], splitValue[1]);
                }
                int result = ot.operator.apply(NumeralTypes.getIntValue(splitValue[0]), NumeralTypes.getIntValue(splitValue[1]));
                if (nt1 == NumeralTypes.ROMAN) {
                    if (result <= 0) {
                        throw new IllegalInputException(stringValue);
                    }
                    return RomanNumerals.intToStringRomanNumerals(result);
                }
                return String.valueOf(result);
            }
        }
        throw new IllegalInputException(stringValue);
    }
}
