import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class CameraRotation {

    private int yaw = 0;
    private int pitch = 0;
    private double rotationSpeed;
    private double zoomSpeed;
    private final double DEFAULT_RADIUS = 1000;
    private double distance = 1;
    private Camera camera;
    private Point center;

    public CameraRotation(Point center, double rotationSpeed, double zoomSpeed) {
        this.center = center;
        this.rotationSpeed = rotationSpeed;
        this.zoomSpeed = zoomSpeed;
        this.camera = new PerspectiveCamera(true) {{
            setFarClip(5000);
            setTranslateZ(center.getZ() - DEFAULT_RADIUS);
            getTransforms().add(new Rotate(0, Rotate.X_AXIS));
        }};
    }

    private void calculate() {
        normalizeValues();
        double rotationRadius = Math.cos(Math.toRadians(pitch)) * DEFAULT_RADIUS * distance;
        double x = - Math.sin(Math.toRadians(yaw)) * rotationRadius + center.getX();
        double y = Math.sin(Math.toRadians(pitch)) * DEFAULT_RADIUS * distance + center.getY();
        double z = - Math.cos(Math.toRadians(yaw)) * rotationRadius + center.getZ();
        camera.setTranslateX(x);
        camera.setTranslateY(y);
        camera.setTranslateZ(z);
        Rotate rotateYaw = new Rotate(yaw, Rotate.Y_AXIS);
        Rotate rotatePitch = new Rotate(pitch, Rotate.X_AXIS);
        camera.getTransforms().set(0, rotateYaw.createConcatenation(rotatePitch));
    }

    public void move(Direction dir) {
        switch (dir) {
            case LEFT -> yaw += rotationSpeed;
            case RIGHT -> yaw -= rotationSpeed;
            case DOWN -> pitch += rotationSpeed;
            case UP -> pitch -= rotationSpeed;
            case ZOOM_OUT -> distance += zoomSpeed;
            case ZOOM_IN -> distance -= zoomSpeed;
        }
        calculate();
    }



    private void normalizeValues() {
        yaw %= 360;
        if (pitch > 90) {
            pitch = 90;
        } else if (pitch < -90) {
            pitch = -90;
        }
        if (distance < 0) {
            distance = 0;
        } else if (distance > 4) {
            distance = 4;
        }
    }

    public Camera getCamera() {
        return camera;
    }

}
