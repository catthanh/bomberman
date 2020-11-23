package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.input.KeyPolling;
import uet.oop.bomberman.utilities.GameLoopTimer;

public class Game extends Canvas {

    //size
    public static final int TILE_SIZE = 16,
    TILE_WIDTH = 20,
    TILE_HEIGHT = 15,
    WIDTH = TILE_SIZE*TILE_WIDTH,
    HEIGHT = TILE_SIZE*TILE_HEIGHT,
    CAMERA_WIDTH = 16,
    CAMERA_HEIGHT = 13;
    public static int SCALE = 3;
    public static final double PLAYER_SPEED = 1.0;


    Board _board;
    Camera _camera;
    GraphicsContext _gc;
    KeyPolling _input=KeyPolling.getInstance();

    public Game(double v, double v1) {
        super(v, v1);
        _gc = this.getGraphicsContext2D();
        _camera=new Camera(CAMERA_WIDTH*SCALE,CAMERA_HEIGHT*SCALE,_gc);
        _board=new Board(this);
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
        _board.render(_camera);
    }

    private void update(double secondsSinceLastFrame) {
        _board.update(secondsSinceLastFrame);
    }
}
