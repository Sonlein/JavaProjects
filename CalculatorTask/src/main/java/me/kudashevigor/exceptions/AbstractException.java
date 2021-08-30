package me.kudashevigor.exceptions;

public abstract class AbstractException extends Exception {

    private final String message;
    private final String exceptionCode;

    public AbstractException(String message, String exceptionCode) {
        this.message = message;
        this.exceptionCode = "#C" + exceptionCode;
    }

    @Override
    public String getMessage() {
        return String.format(
                """
                Произошла ошибка: %s
                Код ошибки: %s
                """,
                message, exceptionCode);
    }

}
