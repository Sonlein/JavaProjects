import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class World {

    private static WorldProperties wp = new WorldProperties();

    private static HashMap<Location, Cell> world = new HashMap<>();
    private static ArrayList<Cell> queueToUpdate = new ArrayList<>();

    private static Location cameraLocation = new Location(0, 0);

    public static void addToWorld(Cell cell) {
        world.put(cell.getLocation(), cell);
    }

    public static Cell getCell(Location loc) {
        return world.get(loc);
    }

    public static void randomize(int chance) {
        clear();
        if (chance <= 0) {
            chance = 0;
        } else if (chance >= 100) {
            chance = 100;
        }
        Random rand = new Random();
        for (int x = 0; x < wp.getWidth(); x++) {
            for (int y = 0; y < wp.getHeight(); y++) {
                Location loc = new Location(x, y);
                if (chance >= rand.nextInt(100)) {
                    Cell cell = new Cell(loc);
                    addToWorld(cell);
                }
            }
        }
    }

    public static void clear() {
        world.clear();
    }

    public static void addToQueue(Cell cell) {
        cell.setInQueue(true);
        queueToUpdate.add(cell);
    }

    public static Location getCameraLocation() {
        return cameraLocation.copy();
    }

    public static void move(Direction dir) {
        cameraLocation = new Location(cameraLocation.getX() + (dir.getDeltaX() * 2), cameraLocation.getY() + (dir.getDeltaY() * 2));
    }

    public static Cell switchCellState(Location loc) {
        Cell cell = getCell(loc);
        if (cell == null) {
            cell = new Cell(loc, CellState.DEAD);
            World.addToWorld(cell);
        }
        switch (cell.getState()) {
            case DEAD -> cell.revive();
            case ALIVE -> cell.kill();
        }
        return cell;
    }

    public static int updateWorld() {
        world.forEach((loc, cell) -> {
            cell.setCheckNeighbours(true);
            addToQueue(cell);
        });
        HashMap<Location, Cell> newWorld = new HashMap<>();
        for (int i = 0; i < queueToUpdate.size(); i++) {
            Cell cell = queueToUpdate.get(i);
            if (cell.calculateFutureState() == CellState.ALIVE) {
                newWorld.put(cell.getLocation(), cell.getChild());
            }
        }
        queueToUpdate.clear();
        world = newWorld;
        return world.size();
    }

    public static void setWorldProperties(WorldProperties wp) {
        World.wp = wp.copy();
    }

    public static WorldProperties getWorldProperties() {
        return wp.copy();
    }

}
