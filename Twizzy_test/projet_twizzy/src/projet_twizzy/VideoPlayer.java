package projet_twizzy;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class VideoPlayer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String path = "https://arche.univ-lorraine.fr/pluginfile.php/1004382/mod_resource/content/0/video1.mp4";
        //Media media = new Media(new File(path).toURI().toString());
        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Pane pane = new Pane();
        pane.getChildren().add(mediaView);
        Scene scene = new Scene(pane, 640, 480);
        //mediaView.setFitWidth(scene.getWidth());
        mediaView.setFitHeight(scene.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        mediaPlayer.play();
    }
}
