package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.input.KeyPolling;
import uet.oop.bomberman.utilities.GameLoopTimer;

import java.io.IOException;

public class Game extends Canvas {

    //size
    public static final int TILE_SIZE = 16,
            SCALE = 3,
            CAMERA_WIDTH = 16,
            CAMERA_HEIGHT = 13;
    public static final double PLAYER_SPEED = 1.0;


    Board _board;
    ScorePanel _panel;
    Camera _camera;
    KeyPolling _input = KeyPolling.getInstance();
    public static GamePhase _phase = GamePhase.GAME_MENU;
    double levelPreLoadTimeLeft = 1.0;


    public Game(double width, double height) {
        super(width, height);
        _camera = new Camera((int) width, (int) height, this.getGraphicsContext2D());
        _board = new Board(this);
        _panel = new ScorePanel(this);
    }

    public void start() {
        //gem loop
        GameLoopTimer timer = new GameLoopTimer() {

            @Override
            public void tick(double secondsSinceLastFrame) {
                update(secondsSinceLastFrame);
                render();
            }
        };
        timer.start();

    }

    private void render() {
        switch (_phase) {
            case GAME_PLAY: {
                _board.render(_camera);
                _panel.render();
                break;
            }
            case GAME_MENU: {
                getGraphicsContext2D().setFill(Color.rgb(0, 0, 0));
                getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());
                getGraphicsContext2D().setFill(Color.rgb(255, 255, 0));
                Font f = new Font(100);
                getGraphicsContext2D().setFont(f);
                getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
                getGraphicsContext2D().fillText("BOMBERMAN", getWidth() / 2, getHeight() / 2, getWidth());
                getGraphicsContext2D().setFont(new Font(80));
                getGraphicsContext2D().fillText("Play", getWidth() / 2, getHeight() * 7 / 10, getWidth());
                break;
            }
            case GAME_OVER: {
                getGraphicsContext2D().setFill(Color.rgb(0, 0, 0));
                getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());
                getGraphicsContext2D().setFill(Color.rgb(255, 255, 0));
                Font f = new Font(100);
                getGraphicsContext2D().setFont(f);
                getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
                getGraphicsContext2D().fillText("GAME OVER", getWidth() / 2, getHeight() / 2, getWidth());
                getGraphicsContext2D().setFont(new Font(80));
                getGraphicsContext2D().fillText("Play again", getWidth() / 2, getHeight() * 7 / 10, getWidth());
                break;
            }
            case GAME_LEVEL_PRELOAD: {
                getGraphicsContext2D().setFill(Color.rgb(0, 0, 0));
                getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());
                getGraphicsContext2D().setFill(Color.rgb(255, 255, 0));
                Font f = new Font(100);
                getGraphicsContext2D().setFont(f);
                getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
                getGraphicsContext2D().fillText("Level" + Board.level, getWidth() / 2, getHeight() / 2, getWidth());
                break;
            }
        }

    }

    private void update(double secondsSinceLastFrame) {
        switch (_phase) {
            case GAME_PLAY: {
                _panel.update(secondsSinceLastFrame);
                _board.update(secondsSinceLastFrame);
                break;
            }
            case GAME_MENU: {
                if (_input.isDown(KeyCode.ENTER)) {
                    _phase = GamePhase.GAME_LEVEL_PRELOAD;
                }
                break;
            }
            case GAME_OVER: {
                if (_input.isDown(KeyCode.ENTER)) {
                    _phase = GamePhase.GAME_PLAY;
                }
                break;
            }
            case GAME_LEVEL_PRELOAD: {
                levelPreLoadTimeLeft -= secondsSinceLastFrame;
                if (levelPreLoadTimeLeft < 0) {
                    _phase = GamePhase.GAME_PLAY;
                    levelPreLoadTimeLeft = 1.0;
                }
            }
        }

    }
}
