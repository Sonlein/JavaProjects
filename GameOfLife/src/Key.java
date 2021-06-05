public enum Key {

    RANDOMIZE(82),
    CLEAR(67),
    SPEED_UP(61),
    SPEED_DOWN(45),
    PAUSE(32),
    LEFT_MOUSE(1),
    RIGHT_MOUSE(3),
    ARROW_LEFT(37),
    ARROW_UP(38),
    ARROW_RIGHT(39),
    ARROW_DOWN(40),
    ANOTHER(-1);

    private int code;

    Key(int code) {
        this.code = code;
    }

    private int getCode() {
        return code;
    }

    public static Key getKey(int code) {
        for (Key key : values()) {
            if (key.getCode() == code) {
                return key;
            }
        }
        return ANOTHER;
    }

}
