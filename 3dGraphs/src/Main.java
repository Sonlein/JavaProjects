import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        GraphProperties gp = new GraphProperties(
                new AxisProperties(-1, 2),
                new AxisProperties(-100, 100),
                new AxisProperties(-1, 2)
        );

        GraphManager.add(gp, Math::pow);
        GraphManager.add(gp, Double::sum);
        GraphManager.add(gp, (x, z) -> (x * x + z * z) / (x - z));
        GraphManager.add(gp, (x, z) -> Math.pow(x, 3) / Math.pow(z, 2));
        GraphManager.add(gp, (x, z) -> {
            double a;
            double b;
            if (x > 0) {
                a = x;
                b = z;
            } else {
                a = z;
                b = x;
            }
            return (Math.sin(a) + Math.cos(b)) / ((Math.pow(a, 3) + Math.pow(b, 2)) - (Math.pow(b, 3) + Math.pow(a, 2)));
        });

        GraphManager.initialize(stage);



    }
}
