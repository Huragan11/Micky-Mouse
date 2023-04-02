package Stage;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CrackedEggLabel extends Label {

    Image missGraphic = new Image("GraphicFiles\\miss.png");
    ImageView missView = new ImageView(missGraphic);
    Timeline timeline;

    public CrackedEggLabel() {

        setPrefSize(40,40);
        setGraphic(missView);
        setVisible(false);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(250), event -> setVisible(false));
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), event -> setVisible(true));
        timeline = new Timeline(keyFrame, keyFrame2);
        timeline.setCycleCount(Animation.INDEFINITE);




    }
    public void startAnimation(){
        timeline.play();
    }
    public void stopAnimation(){
        timeline.stop();
        setVisible(true);
    }




}
