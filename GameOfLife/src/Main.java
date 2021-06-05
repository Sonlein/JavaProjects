import javax.swing.*;
import java.util.Arrays;

public class Main extends JFrame {

    private static final String HELP =
            """
            
            [ Game Of Life ]
            ================================================================================================================
                -width : ширина поля (default %d)
                -height : высота поля (default %d)
                -cellsize : размер клетки в пикселях (default %d)
                -bordersize : размер контура клетки от 0 до 1 (default %.1f)
                -cellstolive : кол-во клеток вокруг живой клетки для выживания клетки через запятую (default %s)
                -cellstoborn : кол-во клеток вокруг мертвой клетки для возрождения клетки через запятую (default %s)
                -lifespan : кол-во ходов подряд клетке необходимо выжить чтобы полностью пройти цикл окрашивания (default %d)
            ================================================================================================================
            
            [ Управление ]
            ================================================================================================================
                R : заполнить поле случайным образом
                C : очистить поле
                + : увеличить скорость мира
                - : уменьшить скорость мира
                Пробел : остановить/запустить мир
                ЛКМ : оживить/убить клетку
                Стрелки : перемещение
                
                При создании поля мир автоматическм ставится на паузу. Для создания и рисования паттернов рекомендуется
                самостоятельно останавливать мир, так как иначе клетки могут умирать
                Реальная геометрия поля - тор, что означает, что клетки, зашедшие за границу поля с одной стороны,
                появятся на противоположной границе поля
            ================================================================================================================
            """;

    private static final String INVALID_ARG = "Неизвестный аргумент %s, список доступных аргументов выводится при запуске программы";

    public Main(WorldProperties wp) {
        setTitle("Game Of Life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        World.setWorldProperties(wp);
        setSize(wp.getScreenWidth(), wp.getScreenHeight());

        GameActivity gameActivity = new GameActivity();
        addKeyListener(gameActivity);
        addMouseListener(gameActivity);
        add(gameActivity);
    }

    public static void main(String[] args) {
        WorldProperties wp = new WorldProperties();
        int i = 0;
        int steps;
        while (i < args.length) {
            String arg = args[i].toLowerCase();
            if (i + 1 < args.length && args[i + 1].charAt(0) != '-') {
                String value = args[i + 1];
                steps = 2;
                switch (arg) {
                    case "-width" -> wp.setWidth((int) WorldProperties.normalizeValue(value));
                    case "-height" -> wp.setHeight((int) WorldProperties.normalizeValue(value));
                    case "-cellsize" -> wp.setCellSize((int) WorldProperties.normalizeValue(value));
                    case "-bordersize" -> wp.setBorderSize(WorldProperties.normalizeValue(value));
                    case "-cellstolive" -> wp.setCellsToLive(WorldProperties.normalizeValue(value, ","));
                    case "-cellstoborn" -> wp.setCellsToBorn(WorldProperties.normalizeValue(value, ","));
                    case "-lifespan" -> wp.setLifespan((int) WorldProperties.normalizeValue(value));
                    default -> System.out.println(String.format(INVALID_ARG, arg));
                }
            } else {
                steps = 1;
                System.out.println(String.format(INVALID_ARG, arg));
            }
            i += steps;
        }
        System.out.println(String.format(
                HELP,
                WorldProperties.DEFAULT_WIDTH,
                WorldProperties.DEFAULT_HEIGHT,
                WorldProperties.DEFAULT_CELL_SIZE,
                WorldProperties.DEFAULT_BORDER_SIZE,
                Arrays.toString(WorldProperties.DEFAULT_CELLS_TO_LIVE),
                Arrays.toString(WorldProperties.DEFAULT_CELLS_TO_BORN),
                WorldProperties.DEFAULT_LIFESPAN));
        Main main = new Main(wp);
    }
}
