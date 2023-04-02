package Stage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class GameController {

    int spawnPositionRand;
    int spawnerSleeper = 1500;

    double width = 640;
    double height = 480;

    boolean UP_LEFT;
    boolean DOWN_LEFT;
    boolean UP_RIGHT;
    boolean DOWN_RIGHT;


    Scene gameScene;
    Pane root = new Pane();

    double missPoints = 0;

    HBox misses = new HBox(new CrackedEggLabel(), new CrackedEggLabel(), new CrackedEggLabel(), new CrackedEggLabel());

    Label crackLeft = new Label();
    Image crackGraphic = new Image("GraphicFiles\\eggBreak.png");
    ImageView eggCrackViewLeft = new ImageView(crackGraphic);
    Label crackRight = new Label();
    ImageView eggCrackViewRight = new ImageView(crackGraphic);

    Thread missesChecker = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (missPoints == 0.5) {
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(3);
                            l.startAnimation();
                        } else if (missPoints == 1) {
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(3);
                            l.stopAnimation();

                        } else if (missPoints == 1.5) {
                            CrackedEggLabel past = (CrackedEggLabel) misses.getChildren().get(3);
                            past.stopAnimation();
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(2);
                            l.startAnimation();
                        } else if (missPoints == 2) {
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(2);
                            l.stopAnimation();

                        } else if (missPoints == 2.5) {
                            CrackedEggLabel past = (CrackedEggLabel) misses.getChildren().get(2);
                            past.stopAnimation();
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(1);
                            l.startAnimation();
                        } else if (missPoints == 3) {
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(1);
                            l.stopAnimation();

                        } else if (missPoints == 3.5) {
                            CrackedEggLabel past = (CrackedEggLabel) misses.getChildren().get(1);
                            past.stopAnimation();
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(0);
                            l.startAnimation();
                        } else if (missPoints >= 4) {
                            CrackedEggLabel l = (CrackedEggLabel) misses.getChildren().get(0);
                            l.stopAnimation();

                        }

                    }
                });
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }


        }
    });

    BackgroundSize backgroundSize;
    BackgroundImage backgroundImage;

    Label pointCounter = new Label();
    int points = 0;

    long start;
    long finish;
    long elapsedTime;

    boolean minnieMouseIsLooking = false;
    Label MinnieMouse = new Label();
    Image minnieMouseGraphic = new Image("GraphicFiles\\Minnie.png");
    ImageView viewMinnie = new ImageView(minnieMouseGraphic);

    Label MickyMouse = new Label();
    Image mickyMouseGraphic = new Image("GraphicFiles\\mickyup.png");
    ImageView view = new ImageView(mickyMouseGraphic);

    Thread minnieMouseAppearing = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                MinnieMouse.setVisible(!MinnieMouse.isVisible());
                minnieMouseIsLooking = !minnieMouseIsLooking;
            }
        }
    });

    Thread eggSpawner = new Thread(new Runnable() {
        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                spawnPositionRand = (int) (Math.random() * 4 + 1);

                try {
                    Thread.sleep(spawnerSleeper);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (spawnerSleeper > 700) {
                    spawnerSleeper -= 10;
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Egg egg = new Egg(spawnPositionRand);
                        root.getChildren().add(egg);
                        root.getChildren().add(egg.crack);
                        egg.timeline.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (egg.getStartingPos() == 1 && UP_LEFT) {
                                    pointCounter.setText(String.valueOf(points += 1));
                                    root.getChildren().remove(egg);
                                } else if (egg.getStartingPos() == 2 && DOWN_LEFT) {
                                    pointCounter.setText(String.valueOf(points += 1));
                                    root.getChildren().remove(egg);
                                } else if (egg.getStartingPos() == 3 && UP_RIGHT) {
                                    pointCounter.setText(String.valueOf(points += 1));
                                    root.getChildren().remove(egg);
                                } else if (egg.getStartingPos() == 4 && DOWN_RIGHT) {
                                    pointCounter.setText(String.valueOf(points += 1));
                                    root.getChildren().remove(egg);
                                } else {
                                    if (!minnieMouseIsLooking) {
                                        missPoints += 0.5;
                                    } else {
                                        missPoints += 1;
                                    }
                                    egg.falling.play();
                                }

                            }
                        });
                    }
                });
            }
        }
    });

    TextInputDialog submit = new TextInputDialog("Your name");
    Optional<String>  result;
    String name;
    boolean gameover = false;
    public GameController() {



        start = System.nanoTime();

        crackLeft.setGraphic(eggCrackViewLeft);
        crackRight.setGraphic(eggCrackViewRight);
        crackLeft.setVisible(false);
        crackRight.setVisible(false);

        misses.setLayoutX(275);
        misses.setSpacing(5);
        misses.setLayoutY(100);
        misses.getChildren().addAll();


        pointCounter.setText(String.valueOf(points));
        pointCounter.setLayoutX(400);
        pointCounter.setLayoutY(25);
        pointCounter.setFont(new Font("Courier", 40));

        MinnieMouse.setLayoutX(115);
        MinnieMouse.setLayoutY(20);
        MinnieMouse.setGraphic(viewMinnie);

        MickyMouse.setLayoutX(150);
        MickyMouse.setLayoutY(200);
        MickyMouse.setGraphic(view);

        root.getChildren().addAll(misses, pointCounter, MickyMouse, MinnieMouse);

        backgroundSize = new BackgroundSize(640,
                480,
                true,
                true,
                true,
                false);
        backgroundImage = new BackgroundImage(new Image("GraphicFiles\\gameBackground.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);

        root.setBackground(new Background(backgroundImage));
        gameScene = new Scene(root, width, height);

        minnieMouseAppearing.start();
        missesChecker.start();
        eggSpawner.start();

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> setMickyPosition_UP_LEFT();
                case Z -> setMickyPosition_DOWN_LEFT();
                case D -> setMickyPosition_UP_RIGHT();
                case X -> setMickyPosition_DOWN_RIGHT();
            }
            if (new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN).match(event)){
                eggSpawner.interrupt();
                missesChecker.interrupt();
                minnieMouseAppearing.interrupt();
                finish = System.nanoTime();
                elapsedTime = finish - start;
                elapsedTime = TimeUnit.SECONDS.convert(elapsedTime,TimeUnit.NANOSECONDS);
                String runInfo = "Your time in seconds: " + elapsedTime + ", Your score: " + points;
                submit.setHeaderText(runInfo);
                name = submit.getEditor().getText();
                result = submit.showAndWait();
                if (result.isPresent()) {
                    name = submit.getEditor().getText();
                    String fullRunInfo = "Name: " + name + ", " + runInfo + "\n";
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\huber\\Desktop\\MickyMouseRecords.txt", true));
                        writer.append(fullRunInfo);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    gameover = true;
                }
            }
        });
    }
    public void gameBegin(){
        minnieMouseAppearing.start();
        missesChecker.start();
        eggSpawner.start();
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void setMickyPosition_UP_LEFT() {
        mickyMouseGraphic = new Image("GraphicFiles\\mickyup.png");
        view = new ImageView(mickyMouseGraphic);
        MickyMouse.setGraphic(view);
        MickyMouse.setLayoutX(150);
        MickyMouse.setLayoutY(200);
        UP_LEFT = true;
        DOWN_LEFT = false;
        UP_RIGHT = false;
        DOWN_RIGHT = false;
    }

    public void setMickyPosition_DOWN_LEFT() {
        mickyMouseGraphic = new Image("GraphicFiles\\mickydownLeft.png");
        view = new ImageView(mickyMouseGraphic);
        MickyMouse.setGraphic(view);
        MickyMouse.setLayoutX(150);
        MickyMouse.setLayoutY(220);
        UP_LEFT = false;
        DOWN_LEFT = true;
        UP_RIGHT = false;
        DOWN_RIGHT = false;
    }

    public void setMickyPosition_UP_RIGHT() {
        mickyMouseGraphic = new Image("GraphicFiles\\mickyupRight.png");
        view = new ImageView(mickyMouseGraphic);
        MickyMouse.setGraphic(view);
        MickyMouse.setLayoutX(300);
        MickyMouse.setLayoutY(200);
        UP_LEFT = false;
        DOWN_LEFT = false;
        UP_RIGHT = true;
        DOWN_RIGHT = false;
    }

    public void setMickyPosition_DOWN_RIGHT() {
        mickyMouseGraphic = new Image("GraphicFiles\\mickydown.png");
        view = new ImageView(mickyMouseGraphic);
        MickyMouse.setGraphic(view);
        MickyMouse.setLayoutX(300);
        MickyMouse.setLayoutY(220);
        UP_LEFT = false;
        DOWN_LEFT = false;
        UP_RIGHT = false;
        DOWN_RIGHT = true;
    }


}
