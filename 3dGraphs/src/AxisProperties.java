public class AxisProperties {

    private double min;
    private double max;
    private double step;

    public AxisProperties(double min, double max) {
        this.min = (double) Math.round(min * 100) / 100;
        this.max = (double) Math.round(max * 100) / 100;
        this.step = (max - min) / 100;
    }

    public AxisProperties(double min, double max, double step) {
        this(min, max);
        this.step = (double) Math.round(step * 100) / 100;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }
}
