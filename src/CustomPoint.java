import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class CustomPoint {

    private static final Color color = Color.GREEN;
    private static final Rotate r1 = new Rotate(90, Rotate.Y_AXIS);
    private static final Rotate r2 = new Rotate(90, Rotate.Z_AXIS);

    public static Group create(GraphProperties gp, Point point) {

        Group group = new Group();

        Line l1 = getLine(gp, point);
        Line l2 = getLine(gp, point);
        l2.getTransforms().add(r1);
        Line l3 = getLine(gp, point);
        l3.getTransforms().add(r2.createConcatenation(r1));

        group.getChildren().addAll(l1, l2, l3);

        return group;
    }

    private static Line getLine(GraphProperties gp, Point point) {
        return new Line() {{
            setStartX(0);
            setEndX(gp.getScale() * 0.01);
            setStartY(0);
            setEndY(gp.getScale() * 0.01);
            setTranslateX(point.getX() * gp.getScale());
            setTranslateY(point.getY() * gp.getScale());
            setTranslateZ(point.getZ() * gp.getScale());
            setStroke(getColor(gp, point.getY()));
        }};
    }

    private static Color getColor(GraphProperties gp, double value) {
        double middleValue = (gp.getAxisY().getMin() + gp.getAxisY().getMax()) / 2;
        double delta = value - middleValue;
        double r = 0;
        double g = 0;
        double b = 0;
        if (delta > 0) {
            b = delta / (gp.getAxisY().getMax() - middleValue) * gp.getScale() / 2;
            g = 1 - b;
        } else if (delta < 0) {
            r = delta / (gp.getAxisY().getMin() - middleValue) * gp.getScale() / 2;
            g = 1 - r;
        } else {
            g = 1;
        }
        return Color.color(normalizeColorValue(r), normalizeColorValue(g), normalizeColorValue(b));
    }

    private static double normalizeColorValue(double value) {
        if (value < 0) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        return value;
    }

}
