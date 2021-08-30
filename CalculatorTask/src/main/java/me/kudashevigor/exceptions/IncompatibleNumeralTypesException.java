package me.kudashevigor.exceptions;

public class IncompatibleNumeralTypesException extends AbstractException {

    public IncompatibleNumeralTypesException(String n1, String n2) {
        super(String.format("Числа %s и %s нельзя использовать вместе, так как они разного типа", n1, n2), "1");
    }

}
