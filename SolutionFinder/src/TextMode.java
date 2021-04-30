public enum TextMode {

    RED(31),
    GREEN(32),
    BLUE(34),
    RESET(0);

    private final int CODE;
    private static final String PREFIX = "\033[";
    private static final String POSTFIX = "m";
    public static boolean enabled = true;

    TextMode(int code) {
        CODE = code;
    }

    @Override
    public String toString() {
        return enabled ? PREFIX + CODE + POSTFIX : "";
    }


}
