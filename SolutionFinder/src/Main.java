import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    /*
    ACCURACY - точность вычисления, сколько знаков должно быть вычислено
    NUMBER - значение f(x) функции, в которой ищем значение x (в данном примере ищем 1.41..(корень из 2), где f(x) = 2)
    LOOP_AMOUNT - количество повторений каждого алгоритма (для тестирования производительности в вычислениях с небольшой точностью)

    В файле src/root<number>.txt находится проверенное искомое значение, где <number> это константа NUMBER (2, 3, 123, 1.5, 2.523, -1.2)
    Если файла не существует, проверка значения не будет выполнена

    При проверке символы помечаются цветами, верный символ - зеленый, неверный - красный, файл для проверки отсутствует
    (или точность проверяемого числа превышает точность числа в файле) - синий

    В методе getFunc() необходимо указать логику функции f(x), в данном примере f(x) = x * x
     */
    
    static final int ACCURACY = 1000;
    static final double NUMBER = 2;
    static final int LOOP_AMOUNT = (int) Math.pow(10, 0);

    static final String OUTPUT =
            "%nВремя поиска решения %s способом: %dмс" +
            "%nРезультат: %s" +
            "%nКоличество верно вычисленных символов: %d" +
            "%n%n";



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Включить отображение цветов? \n0 - нет \n1 - да \nВНИМАНИЕ: Цвета не отображаются при использовании встроенной консоли Windows");
            try {
                int a = Integer.parseInt(scanner.nextLine());
                if (a == 0) {
                    TextMode.enabled = false;
                    break;
                } else if (a == 1) {
                    break;
                }
            } catch (Exception ignored) {

            }
            System.out.println("Неверный ввод!\n");
        }

        System.out.println("\nНажмите Enter, чтобы начать поиск решения\n");
        scanner.nextLine();

        VerifiedValue.initialize(NUMBER);

        long startTime;
        VerifiedValue vv;
        BigDecimal solution = BigDecimal.ZERO;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < LOOP_AMOUNT; i++) {
            solution = getSolution1();
        }
        vv = new VerifiedValue(solution);
        System.out.printf(OUTPUT, "первым", System.currentTimeMillis() - startTime, vv.getVerifiedValue(), vv.getCorrectDigitsAmount());


        startTime = System.currentTimeMillis();
        for (int i = 0; i < LOOP_AMOUNT; i++) {
            solution = getSolution2();
        }
        vv = new VerifiedValue(solution);
        System.out.printf(OUTPUT, "вторым", System.currentTimeMillis() - startTime, vv.getVerifiedValue(), vv.getCorrectDigitsAmount());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < LOOP_AMOUNT; i++) {
            solution = getSolution3();
        }
        vv = new VerifiedValue(solution);
        System.out.printf(OUTPUT, "третьим", System.currentTimeMillis() - startTime, vv.getVerifiedValue(), vv.getCorrectDigitsAmount());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < LOOP_AMOUNT; i++) {
            solution = getSolution4();
        }
        vv = new VerifiedValue(solution);
        System.out.printf(OUTPUT, "четвертым", System.currentTimeMillis() - startTime, vv.getVerifiedValue(), vv.getCorrectDigitsAmount());

        System.out.println("\nНажмите Enter, чтобы закрыть окно\n");
        scanner.nextLine();

    }

    public static BigDecimal getFunc(BigDecimal x) {
        return x.multiply(x);
    }

    /*
    Тернарный (троичный) поиск
    Время вычисления 1000 символов +- 1200мс
     */
    public static BigDecimal getSolution1() {

        BigDecimal right = BigDecimal.TEN.pow(ACCURACY);
        BigDecimal left = right.negate();
        BigDecimal middle = BigDecimal.valueOf(1);
        BigDecimal number = BigDecimal.valueOf(NUMBER);

        final BigDecimal EPSILON = BigDecimal.ONE.movePointLeft(ACCURACY);
        final BigDecimal TWO = BigDecimal.valueOf(2);

        while (right.subtract(left).compareTo(EPSILON) > 0) {
            if (getFunc(middle).compareTo(number) >= 0) {
                right = middle.add(BigDecimal.ZERO);
            } else {
                left = middle.add(BigDecimal.ZERO);
            }
            middle = (right.add(left).divide(TWO));
        }
        return right;
    }

    /*
    Посимвольный поиск
    Время вычисления 1000 символов +- 20мс
     */
    public static BigDecimal getSolution2() {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal number = BigDecimal.valueOf(NUMBER);
        BigDecimal lastCorrectResult = BigDecimal.ZERO;
        BigDecimal tempResult = BigDecimal.ZERO;
        for (int i = 0; i < ACCURACY; i++) {
            for (int j = 0; j <= 10; j++) {
                if (j != 10) {
                    tempResult = result.add(BigDecimal.valueOf(j).movePointLeft(i));
                }
                if (j != 0 && (getFunc(tempResult).compareTo(number) >= 0 || j == 10)) {
                    result = lastCorrectResult;
                    break;
                }
                lastCorrectResult = tempResult;
            }
        }
        return result;
    }

    /*
    Итерационная формула Герона
    Время вычисления 1000 символов +- 6500мс
     */
    public static BigDecimal getSolution3() {
        BigDecimal xn = BigDecimal.valueOf(NUMBER).divide(BigDecimal.valueOf(2), RoundingMode.HALF_DOWN);
        for (int i = 0; i < ACCURACY; i++) {
            xn = xn.add(BigDecimal.valueOf(NUMBER).add(BigDecimal.ONE.divide(BigDecimal.TEN.pow(ACCURACY))).divide(xn, RoundingMode.UP)).divide(BigDecimal.valueOf(2));
        }
        return xn;
    }

    /*
    Посимвольный поиск с использованием бинарного поиска максимально возможного значения символа
    Время вычисления 1000 символов +- 12мс
     */
    public static BigDecimal getSolution4() {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal number = BigDecimal.valueOf(NUMBER);
        int middle;
        for (int i = 0; i < ACCURACY; i++) {
            int left = 0;
            int right = 10;
            BigDecimal tempResult;
            while (right - left > 1) {
                middle = (left + right) / 2;
                tempResult = result.add(BigDecimal.valueOf(middle).movePointLeft(i));
                if (getFunc(tempResult).compareTo(number) > 0) {
                    right = middle;
                } else {
                    left = middle;
                }
            }
            result = result.add(BigDecimal.valueOf(left).movePointLeft(i));
        }
        return result;
    }





}
