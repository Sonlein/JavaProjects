import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

public class VerifiedValue {

    private static char[] constValue;
    private static double number;

    private String verifiedValue;
    private int correctDigitsAmount = 0;


    public VerifiedValue(BigDecimal value) {
        StringBuilder sb = new StringBuilder();
        char[] chars = value.toString().toCharArray();
        boolean isCorrect = true;
        for (int i = 0; i < chars.length; i++) {
            if (i >= constValue.length) {
                sb.append(TextMode.BLUE);
            } else if (chars[i] == constValue[i]) {
                if (isCorrect && chars[i] != '.') {
                    correctDigitsAmount += 1;
                }
                sb.append(TextMode.GREEN);
            } else {
                isCorrect = false;
                sb.append(TextMode.RED);
            }
            sb.append(chars[i]);
        }
        sb.append(TextMode.RESET);
        verifiedValue = sb.toString();
    }


    public static void initialize(double n) {
        number = n;
        Path path = Path.of(String.format("src/root%s.txt", new DecimalFormat("0.#").format(number)));
        constValue = getVerifiedValue(path).toCharArray();
    }

    static String getVerifiedValue(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getVerifiedValue() {
        return verifiedValue;
    }

    public int getCorrectDigitsAmount() {
        return correctDigitsAmount;
    }

}
