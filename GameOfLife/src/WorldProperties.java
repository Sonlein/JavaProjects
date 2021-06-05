import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class WorldProperties {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_CELL_SIZE = 10;
    public static final double DEFAULT_BORDER_SIZE = 0;
    public static final int DEFAULT_LIFESPAN = 100;
    public static final int[] DEFAULT_CELLS_TO_LIVE = {2, 3};
    public static final int[] DEFAULT_CELLS_TO_BORN = {3};

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int cellSize = DEFAULT_CELL_SIZE;
    private double borderSize = DEFAULT_BORDER_SIZE;
    private int lifespan = DEFAULT_LIFESPAN;
    private HashSet<Integer> cellsToLive = new HashSet<>(){{
        for (int i : DEFAULT_CELLS_TO_LIVE) {
            add(i);
        }
    }};
    private HashSet<Integer> cellsToBorn = new HashSet<>(){{
        for (int i : DEFAULT_CELLS_TO_BORN) {
            add(i);
        }
    }};

    private final double MAGIC_NUMBER = 1.013;
    public final Color DEAD_CELL_COLOR = Color.BLACK;

    public WorldProperties() {
    }

    public WorldProperties copy() {
        WorldProperties wp = new WorldProperties();
        wp.setWidth(width);
        wp.setHeight(height);
        wp.setCellSize(cellSize);
        wp.setBorderSize(borderSize);
        wp.setLifespan(lifespan);
        wp.setCellsToLive(cellsToLive);
        wp.setCellsToBorn(cellsToBorn);
        return wp;
    }

    public CellState getAliveCellNextState(Cell cell) {
        return cellsToLive.contains(cell.getAliveNeighbours()) ? CellState.ALIVE : CellState.DEAD;
    }

    public CellState getDeadCellNextState(Cell cell) {
        return cellsToBorn.contains(cell.getAliveNeighbours()) ? CellState.ALIVE : CellState.DEAD;
    }

    public int getScreenWidth() {
        return (int) (cellSize * width * MAGIC_NUMBER) + 4;
    }

    public int getScreenHeight() {
        return (int) (cellSize * height * MAGIC_NUMBER) + 27;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public void setCellSize(int cellSize) {
        if (cellSize > 0) {
            this.cellSize = cellSize;
        }
    }

    public void setBorderSize(double borderSize) {
        if (borderSize >= 0 && borderSize < 1) {
            this.borderSize = borderSize;
        }
    }

    public void setCellsToLive(int ... values) {
        cellsToLive.clear();
        for (int value : values) {
            if (value > 0) {
                cellsToLive.add(value);
            }
        }
    }

    public void setCellsToBorn(int ... values) {
        cellsToBorn.clear();
        for (int value : values) {
            if (value > 0) {
                cellsToBorn.add(value);
            }
        }
    }

    public static double normalizeValue(String value) {
        value = value.replaceAll(",", ".");
        try {
            return Double.parseDouble(value);
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static int[] normalizeValue(String value, String split) {
        ArrayList<Integer> valuesList = new ArrayList<>();
        for (String a : value.split(split)) {
            valuesList.add((int) normalizeValue(a));
        }
        int[] valuesArray = new int[valuesList.size()];
        for (int j = 0; j < valuesArray.length; j++) {
            valuesArray[j] = valuesList.get(j);
        }
        return valuesArray;
    }

    private void setCellsToBorn(HashSet<Integer> array) {
        cellsToBorn = array;
    }

    private void setCellsToLive(HashSet<Integer> array) {
        cellsToLive = array;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellSize() {
        return cellSize;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        if (lifespan > 0) {
            this.lifespan = lifespan;
        }
    }

}
