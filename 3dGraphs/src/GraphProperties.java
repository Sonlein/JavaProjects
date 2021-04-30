public class GraphProperties {

    private AxisProperties axisX;
    private AxisProperties axisY;
    private AxisProperties axisZ;
    private double scale = 100;
    private double dotRadius;

    public GraphProperties(AxisProperties axisX, AxisProperties axisY, AxisProperties axisZ) {
        this(axisX, axisY, axisZ, 100);
    }

    public GraphProperties(AxisProperties axisX, AxisProperties axisY, AxisProperties axisZ, double scale) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.axisZ = axisZ;
        this.scale = (double) Math.round(scale * 100) / 100;
        this.dotRadius = Math.min(axisX.getStep(), axisZ.getStep()) * scale / 2;
    }

    public AxisProperties getAxisX() {
        return axisX;
    }

    public AxisProperties getAxisY() {
        return axisY;
    }

    public AxisProperties getAxisZ() {
        return axisZ;
    }

    public double getDotRadius() {
        return dotRadius;
    }

    public double getScale() {
        return scale;
    }

}
