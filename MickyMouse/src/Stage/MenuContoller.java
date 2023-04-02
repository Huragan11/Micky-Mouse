package Stage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MenuContoller {

    double width = 640;
    double heigth = 480;
    Button startGame;

    Button highScores;
    Button exit;
    VBox root;
    BackgroundSize backgroundSize;
    BackgroundImage backgroundImage;
    Scene menuScene;


    public MenuContoller() {

        startGame = new Button("START GAME");
        startGame.setPrefSize(200, 50);
        startGame.setStyle("-fx-border-color: black;-fx-border-width: 4px; -fx-background-color: transparent;");
        startGame.setFont(Font.font("Helvetica", FontWeight.BOLD,20));

        highScores = new Button("HIGHSCORES");
        highScores.setPrefSize(200, 50);
        highScores.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        highScores.setStyle("-fx-border-color: black;-fx-border-width: 4px; -fx-background-color: transparent;");


        exit = new Button("EXIT");
        exit.setOnAction(event -> System.exit(0));
        exit.setPrefSize(200, 50);
        exit.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        exit.setStyle("-fx-border-color: black;-fx-border-width: 4px; -fx-background-color: transparent;");


        root = new VBox(startGame, highScores, exit);
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

        root.setSpacing(25);
        root.setAlignment(Pos.CENTER);
        menuScene = new Scene(root, width, heigth);
    }

    public Scene getMenuScene() {
        return menuScene;
    }

}
