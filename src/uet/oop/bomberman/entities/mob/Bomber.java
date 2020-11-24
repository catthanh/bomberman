package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Camera;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.WallTile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;
import uet.oop.bomberman.utilities.Convert;

public class Bomber extends Mob {


    Board _board;
    KeyPolling _input;


    public Bomber(int x, int y, Image img, Board board) {
        super(x, y, img);

        _board = board;
        _input = _board.get_input();
    }

    @Override
    public void update(double s) {
        calculateMove(s);
        calculateCameraOffset();
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }

    private void calculateCameraOffset() {
        double xOffset = Camera.getxOffset();
        if (x - Camera.getWIDTH() / 2.0 > 0 && x + Camera.getWIDTH() / 2.0 < Game.PIXEL_WIDTH)
            xOffset = x - Camera.getWIDTH() / 2.0;
        double yOffset = Camera.getyOffset();
        if (y - Camera.getHEIGHT() / 2.0 > 0 && y + Camera.getHEIGHT() / 2.0 < Game.PIXEL_HEIGHT)
            yOffset = y - Camera.getHEIGHT() / 2.0;

        Camera.setOffset(xOffset, yOffset);
    }

    private void calculateMove(double s) {
        double movementSpeed = Game.PLAYER_SPEED * Game.SCALE * Game.TILE_SIZE * 3;

        {
            if (_input.isDown(KeyCode.DOWN)) {

                move(0, s * movementSpeed);
            }
            if (_input.isDown(KeyCode.UP)) {
                move(0, -s * movementSpeed);
            }
            if (_input.isDown(KeyCode.LEFT)) {
                move(-s * movementSpeed, 0);
            }
            if (_input.isDown(KeyCode.RIGHT)) {
                move(s * movementSpeed, 0);
            }
        }

    }

    private void move(double xStep, double yStep) {
        double xx = x + xStep;
        double yy = y + yStep;

        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 12 / 16;
        boolean movable =
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this);
        if (movable) {
            x = xx;
            y = yy;
        }
    }
}
