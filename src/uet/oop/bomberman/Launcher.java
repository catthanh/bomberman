package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;

public class Launcher extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private Game game;


    public static void main(String[] args) {
        Application.launch(Launcher.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        game = new Game(Sprite.SCALED_SIZE * Game.CAMERA_WIDTH, Sprite.SCALED_SIZE * (Game.CAMERA_HEIGHT + 2));


        // Tao root container
        Group root = new Group();
        root.getChildren().add(game);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        // khoi tao input
        KeyPolling.getInstance().pollScene(scene);
        stage.show();


        //gem loop
        game.start();

    }


}
