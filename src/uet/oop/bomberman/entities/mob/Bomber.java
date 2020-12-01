package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;
import uet.oop.bomberman.utilities.Convert;

public class Bomber extends Mob {


    Board _board;
    KeyPolling _input;
    int _animate = 0;


    public Bomber(int x, int y, Image _img, Board board) {
        super(x, y, _img);

        _board = board;
        _input = _board.get_input();
    }

    @Override
    public void update(double s) {
        calculateMove(s);
        chooseSprite(s);
        calculateCameraOffset();
        placeBomb();
    }

    private void placeBomb() {
        if (_input.isDown(KeyCode.SPACE)) {
            int xt = Convert.pixelToTile(x + Sprite.SCALED_SIZE / 2);
            int yt = Convert.pixelToTile(y + Sprite.SCALED_SIZE / 2);
            if (_board.getBomb(xt, yt) == null) {
                Bomb e = new Bomb(xt, yt, this._board);
                _board.addBomb(e);
            }
        }
    }

    private void chooseSprite(double s) {
        int m = 0;
        _animate += s * 1000;
        if (_animate > 10000) _animate = 0;
        if (_animate % 400 < 200) m = 1;
        else m = 2;
        switch (_direction) {
            case 0: {
                if (_moving) {
                    if (m == 1) _img = Sprite.player_right_1.getFxImage();
                    if (m == 2) _img = Sprite.player_right_2.getFxImage();
                } else _img = Sprite.player_right.getFxImage();
                break;
            }
            case 3: {
                if (_moving) {
                    if (m == 1) _img = Sprite.player_up_1.getFxImage();
                    if (m == 2) _img = Sprite.player_up_2.getFxImage();
                } else _img = Sprite.player_up.getFxImage();
                break;
            }
            case 2: {
                if (_moving) {
                    if (m == 1) _img = Sprite.player_left_1.getFxImage();
                    if (m == 2) _img = Sprite.player_left_2.getFxImage();
                } else _img = Sprite.player_left.getFxImage();
                break;
            }
            case 1: {
                if (_moving) {
                    if (m == 1) _img = Sprite.player_down_1.getFxImage();
                    if (m == 2) _img = Sprite.player_down_2.getFxImage();
                } else _img = Sprite.player_down.getFxImage();
                break;
            }

        }
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
            } else if (_input.isDown(KeyCode.UP)) {
                move(0, -s * movementSpeed);
            } else if (_input.isDown(KeyCode.LEFT)) {
                move(-s * movementSpeed, 0);
            } else if (_input.isDown(KeyCode.RIGHT)) {
                move(s * movementSpeed, 0);
            } else _moving = false;
        }

    }

    private void move(double xStep, double yStep) {
        _moving = true;
        if (xStep > 0) _direction = 0; //right
        if (xStep < 0) _direction = 2; //left
        if (yStep > 0) _direction = 1; //top
        if (yStep < 0) _direction = 3; //bot

        double xx = x + xStep;
        double yy = y + yStep;

        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15 / 16;
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
