import java.awt.*;

public class Cell {

    private CellState state;
    private Location loc;
    private int aliveNeighbours;
    private boolean checkNeighbours = false;
    private boolean inQueue = false;
    private int age;

    public Cell(Location loc, CellState state) {
        this(loc);
        setState(state);
    }

    public Cell(Location loc) {
        this.loc = loc;
        this.state = CellState.ALIVE;
    }

    private void countAliveNeighbours() {
        aliveNeighbours = 0;
        for (Direction dir : Direction.values()) {
            Cell cell = getRelative(dir);
            if (checkNeighbours && (cell == null || cell.getState() == CellState.DEAD)) {
                if (cell == null) {
                    cell = new Cell(loc.getRelative(dir), CellState.DEAD);
                    World.addToWorld(cell);
                }
                if (! cell.inQueue) {
                    World.addToQueue(cell);
                }
            }
            if (cell != null && cell.getState() == CellState.ALIVE) {
                aliveNeighbours += 1;
            }
        }
    }

    public CellState calculateFutureState() {
        WorldProperties wp = World.getWorldProperties();
        countAliveNeighbours();
        age += 1;
        switch (state) {
            case ALIVE -> {
                return wp.getAliveCellNextState(this);
            }
            case DEAD -> {
                return wp.getDeadCellNextState(this);
            }
        }
        return CellState.DEAD;
    }

    public void kill() {
        state = CellState.DEAD;
    }

    public void revive() {
        state = CellState.ALIVE;
    }

    public void setCheckNeighbours(boolean value) {
        checkNeighbours = value;
    }

    public void setInQueue(boolean value) {
        inQueue = value;
    }

    public int getAliveNeighbours() {
        return aliveNeighbours;
    }

    public Color getColor() {
        if (state == CellState.ALIVE) {
            int lifespan = World.getWorldProperties().getLifespan();
            int r;
            int g;
            int b = 0;
            if (age >= 0 && age < lifespan / 2) {
                r = (int) (2.0 * age / lifespan * 255);
                g = 255;
            } else if (age < lifespan) {
                r = 255;
                g = (int) (2.0 * (lifespan - age) / lifespan * 255);
            } else {
                r = 255;
                g = 0;
            }
            return new Color(r, g, b);
        } else {
            return World.getWorldProperties().DEAD_CELL_COLOR;
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void setState(CellState state) {
        this.state = state;
    }

    public static Cell getRelative(Cell cell, Direction dir) {
        return World.getCell(cell.getLocation().getRelative(dir));
    }

    public Cell getRelative(Direction dir) {
        return getRelative(this, dir);
    }

    public Location getLocation() {
        return loc;
    }

    public CellState getState() {
        return state;
    }

    public Cell getChild() {
        Cell child = new Cell(loc);
        child.setAge(age);
        return child;
    }

    public String toString() {
        return String.format("Cell [%s | State: %s | Neighbours: %d]", loc, state, aliveNeighbours);
    }

}