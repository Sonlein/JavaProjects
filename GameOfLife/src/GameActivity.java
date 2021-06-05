import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameActivity extends JPanel implements ActionListener, KeyListener, MouseListener {

    private boolean timeStop = true;
    private int speed = 3;
    private int deltaSpeed = 3;
    private Timer timer = new Timer(getDelay(), this);

    public GameActivity() {
        timer.start();
    }

    private int getDelay() {
        if (speed == 0) {
            return 1000;
        }
        return 1000 / speed;
    }

    private void setSpeed(int value) {
        if (value <= 0) {
            timeStop = true;
            speed = 0;
        } else {
            timeStop = false;
            speed = value;
        }
        timer.setDelay(getDelay());
    }

    public void paint(Graphics g) {
        WorldProperties wp = World.getWorldProperties();
        Location startLoc = World.getCameraLocation();
            for (int i = 0; i < wp.getWidth(); i++) {
                for (int j = 0; j < wp.getHeight(); j++) {
                    Location loc = new Location(i + startLoc.getX(), j + startLoc.getY());
                    Cell cell = World.getCell(loc);

                    if (cell == null || cell.getState() == CellState.DEAD) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(cell.getColor());
                    }
                    int x = i * wp.getCellSize();
                    int y = j * wp.getCellSize();
                    int rectSize = (int) (wp.getCellSize() * (1 - wp.getBorderSize()));
                    g.fillRect(x, y, rectSize, rectSize);
                }
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (! timeStop) {
            World.updateWorld();
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Location loc = Location.getLocationByCoordinates(getMousePosition());
        World.switchCellState(loc);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Key key = Key.getKey(e.getKeyCode());
        switch (key) {
            case PAUSE -> timeStop = !timeStop;
            case SPEED_UP -> setSpeed(speed + deltaSpeed);
            case SPEED_DOWN -> setSpeed(speed - deltaSpeed);
            case RANDOMIZE -> World.randomize(50);
            case CLEAR -> World.clear();
            case ARROW_LEFT -> World.move(Direction.LEFT);
            case ARROW_UP -> World.move(Direction.UP);
            case ARROW_RIGHT -> World.move(Direction.RIGHT);
            case ARROW_DOWN -> World.move(Direction.DOWN);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
