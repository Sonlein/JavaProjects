public enum TextMode {

    RED(31),
    GREEN(32),
    BLUE(34),
    RESET(0);

    private final int CODE;
    private static final String PREFIX = "\033[";
    private static final String POSTFIX = "m";

    TextMode(int code) {
        CODE = code;
    }

    @Override
    public String toString() {
        return PREFIX + CODE + POSTFIX;
    }


}
