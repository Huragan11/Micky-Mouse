package Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class  HighscoresController {

    double width = 640;
    double height = 480;
    Pane root = new Pane();
    List<String> list = new ArrayList<>();
    ListView scores = new ListView();
    Scene highscoresScene;
    Button backButton = new Button("BACK");
    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\huber\\Desktop\\MickyMouseRecords.txt"));
    BackgroundSize backgroundSize;
    BackgroundImage backgroundImage;

    public Scene getHighscoresScene() {
        return highscoresScene;
    }

    public HighscoresController() throws IOException {
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

        list = br.lines().collect(Collectors.toList());
        scores.getItems().addAll(list);
        scores.setPrefSize(350,200);
        scores.setLayoutX(145);
        scores.setLayoutY(140);
        scores.setOpacity(0.55);


        backButton.setPrefSize(100,30);
        backButton.setTextAlignment(TextAlignment.CENTER);
        backButton.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        backButton.setStyle("-fx-border-color: black;-fx-border-width: 4px; -fx-background-color: transparent;");
        backButton.setLayoutX(270);
        backButton.setLayoutY(60);

        root.getChildren().addAll(backButton,scores);
        highscoresScene = new Scene(root, width,height);

    }
}
