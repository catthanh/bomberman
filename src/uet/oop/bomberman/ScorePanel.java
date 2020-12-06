package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class ScorePanel {
    Game _game;
    int _score = 0;
    public static double timeLeft = 200;

    public ScorePanel(Game _game) {
        this._game = _game;
    }

    public void render() {
        _game.getGraphicsContext2D().setFill(Color.rgb(173, 173, 173));
        _game.getGraphicsContext2D().fillRect(0, 0, Board.PIXEL_WIDTH, 2 * Sprite.SCALED_SIZE);
        _game.getGraphicsContext2D().setFill(Color.rgb(255, 255, 255));
        Font f = new Font(40);
        _game.getGraphicsContext2D().setFont(f);
        _game.getGraphicsContext2D().fillText("TIME:", 1 * Sprite.SCALED_SIZE, 1.2 * Sprite.SCALED_SIZE);
        _game.getGraphicsContext2D().fillText(String.valueOf((int) timeLeft), 4 * Sprite.SCALED_SIZE, 1.2 * Sprite.SCALED_SIZE);
        _game.getGraphicsContext2D().fillText("LEFT:", 8 * Sprite.SCALED_SIZE, 1.2 * Sprite.SCALED_SIZE);
        _game.getGraphicsContext2D().fillText(String.valueOf(Bomber.trialLeft), 10 * Sprite.SCALED_SIZE, 1.2 * Sprite.SCALED_SIZE);
    }

    public void update(double secondsSinceLastFrame) {
        timeLeft -= secondsSinceLastFrame;
    }
}
