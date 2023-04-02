package Stage;

import javafx.application.Application;
import java.io.IOException;

public class Stage extends Application {


    MenuContoller menuContoller = new MenuContoller();

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {


        primaryStage.setScene(menuContoller.getMenuScene());

        menuContoller.startGame.setOnAction(event -> {
            GameController gameController = new GameController();
            primaryStage.setScene(gameController.getGameScene());
            gameController.submit.setOnHidden(event1 -> primaryStage.setScene(menuContoller.getMenuScene()));
        });

        menuContoller.highScores.setOnAction(event ->  {
            try {
            HighscoresController highscoresController = new HighscoresController();
            primaryStage.setScene(highscoresController.getHighscoresScene());
            highscoresController.backButton.setOnAction(event12 -> primaryStage.setScene(menuContoller.getMenuScene()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        primaryStage.show();

    }

}
