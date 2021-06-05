import java.awt.*;
import java.util.Objects;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        WorldProperties wp = World.getWorldProperties();
        x %= wp.getWidth();
        y %= wp.getHeight();
        if (x < 0) {
            x += wp.getWidth();
        }
        if (y < 0) {
            y += wp.getHeight();
        }
        this.x = x;
        this.y = y;
    }

    public static Location getLocationByCoordinates(Point point) {
        WorldProperties wp = World.getWorldProperties();
        return new Location(point.x / wp.getCellSize(), point.y / wp.getCellSize());
    }

    public static Location getRelative(Location loc, Direction dir) {
        return new Location(loc.getX() + dir.getDeltaX(), loc.getY() + dir.getDeltaY());
    }

    public Location getRelative(Direction dir) {
        return getRelative(this, dir);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location copy() {
        return new Location(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return String.format("Location [x: %d | y: %d]", x, y);
    }

}
