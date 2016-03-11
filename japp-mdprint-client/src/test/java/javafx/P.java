package javafx;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class P extends Application {

    final Float[] values = new Float[]{-1.0f, 0f, 0.6f, 1.0f};
    final Label[] labels = new Label[values.length];
    final ProgressBar[] pbs = new ProgressBar[values.length];
    final ProgressIndicator[] pins = new ProgressIndicator[values.length];
    // final HBox hbs[] = new HBox[values.length];
    final HBox hbs[] = new HBox[1];

    private static ScheduledService<Void> baseDateSyncServcie;

    @Override
    public void start(Stage stage) {
        baseDateSyncServcie = new ScheduledService<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new TestJob();
            }
        };
        // baseDateSyncServcie.setExecutor(executor);
        baseDateSyncServcie.setPeriod(Duration.hours(TimeUnit.DAYS
                .toHours(3000)));
        baseDateSyncServcie.start();

        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        scene.getStylesheets().add("progresssample/Style.css");
        stage.setScene(scene);
        stage.setTitle("Progress Controls");

        for (int i = 0; i < values.length; i++) {
            final Label label = labels[i] = new Label();
            label.setText("progress:" + values[i]);

            final ProgressBar pb = pbs[i] = new ProgressBar();
            pb.setProgress(values[i]);

            final ProgressIndicator pin = pins[i] = new ProgressIndicator();
            pin.setProgress(values[i]);
            final HBox hb = hbs[i] = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(label, pb, pin);

            pb.progressProperty().bind(baseDateSyncServcie.progressProperty());
            break;
        }

        final VBox vb = new VBox();
        vb.setSpacing(5);
        vb.getChildren().addAll(hbs);
        scene.setRoot(vb);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class TestJob extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            for (int i = 0; i < 10; i++) {
                updateProgress(i + 1, 100);
                Thread.sleep(1000);
            }
            return null;
        }

        @Override
        protected void succeeded() {
            updateProgress(100, 100);
        }
    }
}
