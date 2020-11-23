package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Camera;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Launcher;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;
import uet.oop.bomberman.utilities.CoordinateConverter;

public class Bomber extends Entity {
    int spriteOrder;
    int speedLevel;
    Board _board;
    KeyPolling _input;


    public Bomber(int x, int y, Image img, Board board) {
        super(x, y, img);
        spriteOrder = 0;
        speedLevel=1;
        _board=board;
        _input=_board.get_input();
    }

    @Override
    public void update(double s) {
        calculateMove(s);
        calculateCameraOffset();
    }

    private void calculateCameraOffset() {

    }

    private void calculateMove(double s) {
        double movementSpeed = Game.PLAYER_SPEED*Game.SCALE*Game.TILE_SIZE*2;
        if(_input.isDown(KeyCode.DOWN)) {

            y+= s* movementSpeed;
        }
        if(_input.isDown(KeyCode.UP)) {
            y-= s* movementSpeed;
        }
        if(_input.isDown(KeyCode.LEFT)) {
            x-= s* movementSpeed;
        }
        if(_input.isDown(KeyCode.RIGHT)) {
            x+= s* movementSpeed;
        }

    }
}
