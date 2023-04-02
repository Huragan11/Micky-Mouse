package Stage;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/*
lewa gora X:74 Y:163 1
lewy dol X:74 Y:272 2
prawa gora X:575 Y:163 3
prawy dol X:575 Y:266 4
 */
public class Egg extends Ellipse {

    Thread crackShower = new Thread(new Runnable() {
        @Override
        public void run() {
            setVisible(false);
            crack.setVisible(true);
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            crack.setVisible(false);
        }
    });

    int endValueX;
    int endValueY;
    KeyValue rotate;
    Timeline timeline;
    int startingPos;

    TranslateTransition falling;

    Label crack = new Label();
    Image crackGraphic = new Image("GraphicFiles\\eggBreak.png");
    ImageView eggCrackView = new ImageView(crackGraphic);

    public Egg(int pos) {
        startingPos = pos;
        crack.setPrefSize(90, 50);
        crack.setGraphic(eggCrackView);
        crack.setVisible(false);
        falling = new TranslateTransition(Duration.millis(1250), this);
        falling.setOnFinished(event -> crackShower.start());

        rotate = new KeyValue(this.rotateProperty(), 720);
        switch (pos) {
            case 1:
                setLayoutX(74);
                setLayoutY(163);
                endValueX = 80;
                endValueY = 55;
                falling.setToY(250);
                crack.setLayoutX(120);
                crack.setLayoutY(400);
                break;
            case 2:
                setLayoutX(74);
                setLayoutY(272);
                crack.setLayoutX(120);
                crack.setLayoutY(400);
                endValueX = 80;
                endValueY = 55;
                falling.setToY(150);
                break;
            case 3:
                setLayoutX(575);
                setLayoutY(163);
                crack.setLayoutX(450);
                crack.setLayoutY(400);

                endValueX = -80;
                endValueY = 53;
                falling.setToY(250);
                rotate = new KeyValue(this.rotateProperty(), -720);
                break;
            case 4:
                setLayoutX(575);
                setLayoutY(266);
                crack.setLayoutX(450);
                crack.setLayoutY(400);

                endValueX = -80;
                endValueY = 58;
                falling.setToY(150);
                rotate = new KeyValue(this.rotateProperty(), -720);
                break;
        }

        setRadiusX(7);
        setRadiusY(10);
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
        setStrokeWidth(3);

        KeyValue X = new KeyValue(this.translateXProperty(), endValueX);
        KeyValue Y = new KeyValue(this.translateYProperty(), endValueY);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), X, Y, rotate);
        timeline = new Timeline(keyFrame);
        timeline.play();
    }

    public int getStartingPos() {
        return startingPos;
    }
}
