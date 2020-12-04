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
    Bomb currentBomb = null;
    public static int bombNumber = 1;
    public static int bomRadius = 1;
    public static double speedLevel = 1.0;

    public void increaseBombNumber() {
        bombNumber++;
    }

    public void resetBombNumber() {
        bombNumber++;
    }

    public int getBomRadius() {
        return bomRadius;
    }

    public void setBomRadius(int bomRadius) {
        Bomber.bomRadius = bomRadius;
    }

    public double getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(double speedLevel) {
        this.speedLevel = speedLevel;
    }

    public Bomber(int x, int y, Board board) {
        super(x, y, Sprite.player_right.getFxImage());

        _board = board;
        _input = _board.get_input();
    }

    @Override
    public void update(double s) {
        calculateMove(s);
        chooseSprite(s);
        calculateCameraOffset();
        placeBomb();
        if (currentBomb != null)
            if (passThroughCurrentBomb())
                currentBomb.pass();
    }

    private boolean passThroughCurrentBomb() {
        double top = y;
        double bot = y + Sprite.SCALED_SIZE;
        double left = x;
        double right = x + Sprite.SCALED_SIZE * 12.0 / 16.0;

        return
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)) != currentBomb &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)) != currentBomb &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)) != currentBomb &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)) != currentBomb;
    }

    private void placeBomb() {
        if (_input.isDown(KeyCode.SPACE) && bombNumber > 0) {
            int xt = Convert.pixelToTile(x + Sprite.SCALED_SIZE / 2.0 * 14.0 / 16.0);
            int yt = Convert.pixelToTile(y + Sprite.SCALED_SIZE / 2.0);
            {
                currentBomb = new Bomb(xt, yt, this._board);
                if (!_board.containsBomb(currentBomb)) {
                    _board.addBomb(currentBomb);
                    bombNumber--;
                }
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

    @Override
    public void kill() {

    }

    private void calculateCameraOffset() {
        double xOffset = Camera.getxOffset();
        if (x - Camera.getWIDTH() / 2.0 > 0 && x + Camera.getWIDTH() / 2.0 < Board.PIXEL_WIDTH)
            xOffset = x - Camera.getWIDTH() / 2.0;
        double yOffset = Camera.getyOffset();
        if (y - Camera.getHEIGHT() / 2.0 > 0 && y + Camera.getHEIGHT() / 2.0 < Board.PIXEL_HEIGHT)
            yOffset = y - Camera.getHEIGHT() / 2.0;

        Camera.setOffset(xOffset, yOffset);
    }

    private void calculateMove(double s) {
        double movementSpeed = (speedLevel + 2) * Sprite.SCALED_SIZE;

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
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 12.0 / 16.0;
        double centerX = (left + right) / 2.0;
        double centerY = (top + bot) / 2.0;

        if (xStep < 0) {
            if (_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(centerY)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this)) {
                y += xStep * 0.6;
            }
            if (_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(centerY)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this)) {
                y -= xStep * 0.6;
            }
        }
        if (xStep > 0) {
            if (_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(centerY)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this)) {
                y -= xStep * 0.6;
            }
            if (_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(centerY)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this)) {
                y += xStep * 0.6;
            }
        }
        if (yStep > 0) {
            if (_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(centerX), Convert.pixelToTile(bot)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this)) {
                x += yStep * 0.6;
            }
            if (_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(centerX), Convert.pixelToTile(bot)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this)) {
                x -= yStep * 0.6;
            }
        }
        if (yStep < 0) {
            if (_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(centerX), Convert.pixelToTile(top)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this)) {
                x -= yStep * 0.6;
            }
            if (_board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                    _board.getTilesAt(Convert.pixelToTile(centerX), Convert.pixelToTile(top)).collide(this) &&
                    !_board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this)) {
                x += yStep * 0.6;
            }
        }

        if (movable(xx, yy)) {
            x = xx;
            y = yy;
        }
    }

    public boolean movable(double xx, double yy) {

        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 12.0 / 16.0;

        return
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this);


    }

}
