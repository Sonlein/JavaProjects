import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class GraphManager {

    private static final Rotate r1 = new Rotate(90, Rotate.X_AXIS);
    private static final Rotate r2 = new Rotate(90, Rotate.Z_AXIS);

    private static Color color = Color.LIGHTBLUE;
    private static double rotationSpeed = 5;
    private static double zoomSpeed = 0.01;
    private static Stage stage;
    private static int currentGraph = 0;
    private static ArrayList<Scene> graphs = new ArrayList<>();

    public static void showGraph() {
        if (currentGraph < 0) {
            currentGraph += graphs.size();
        } else if (currentGraph >= graphs.size()) {
            currentGraph %= graphs.size();
        }
        stage.setScene(graphs.get(currentGraph));
    }

    public static void add(GraphProperties gp, BinaryOperator<Double> func) {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth() / 2;
        int height = gd.getDisplayMode().getHeight() / 2;
        Group group = new Group();
        Scene scene = new Scene(group, width, height);
        scene.setFill(color);

        for (double i = gp.getAxisX().getMin(); i < gp.getAxisX().getMax(); i += gp.getAxisX().getStep()) {
            for (double j = gp.getAxisZ().getMin(); j < gp.getAxisZ().getMax(); j += gp.getAxisZ().getStep()) {
                double x = i;
                double z = j;
                double y = func.apply(x, z);
                if (y >= gp.getAxisY().getMin() && y <= gp.getAxisY().getMax()) {
                    group.getChildren().add(CustomPoint.create(gp, new Point(x, y, z)));
                }
            }
        }

        Cylinder xAxis = new Cylinder(Math.min(gp.getAxisX().getStep(), gp.getAxisZ().getStep()) * gp.getScale() / 2, (gp.getAxisY().getMax() - gp.getAxisY().getMin()) * gp.getScale());
        xAxis.getTransforms().add(r1);
        Cylinder yAxis = new Cylinder(Math.min(gp.getAxisX().getStep(), gp.getAxisZ().getStep()) * gp.getScale() / 2, (gp.getAxisY().getMax() - gp.getAxisY().getMin()) * gp.getScale());
        Cylinder zAxis = new Cylinder(Math.min(gp.getAxisX().getStep(), gp.getAxisZ().getStep()) * gp.getScale() / 2, (gp.getAxisY().getMax() - gp.getAxisY().getMin()) * gp.getScale());
        zAxis.getTransforms().add(r2);
        group.getChildren().addAll(xAxis, yAxis, zAxis);

        double centerX = (gp.getAxisX().getMin() + gp.getAxisX().getMax()) / 2 * gp.getScale();
        double centerY = (gp.getAxisY().getMin() + gp.getAxisY().getMax()) / 2 * gp.getScale();
        double centerZ = (gp.getAxisZ().getMin() + gp.getAxisZ().getMax()) / 2 * gp.getScale();

        CameraRotation cr = new CameraRotation(new Point(centerX, centerY, centerZ), rotationSpeed, zoomSpeed);
        scene.setCamera(cr.getCamera());

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case LEFT -> cr.move(Direction.LEFT);
                case RIGHT -> cr.move(Direction.RIGHT);
                case DOWN -> cr.move(Direction.DOWN);
                case UP -> cr.move(Direction.UP);
                case EQUALS -> cr.move(Direction.ZOOM_IN);
                case MINUS -> cr.move(Direction.ZOOM_OUT);
                case OPEN_BRACKET -> {
                     currentGraph -= 1;
                     showGraph();
                }
                case CLOSE_BRACKET -> {
                    currentGraph += 1;
                    showGraph();
                }
                default -> System.out.println(event.getCode());
            }
        });

        graphs.add(scene);
    }

    public static void initialize(Stage s) {
        if (graphs.size() == 0) {
            System.out.println("Ошибка! Необходимо добавить как минимум один график!");
        } else {
            stage = s;
            stage.setScene(graphs.get(0));
            stage.show();
        }
    }

    public static void setColor(Color c) {
        color = c;
    }

}
