package uet.oop.bomberman.entities.mob.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Convert;

public abstract class Enemy extends Mob {
    Image _dead;
    double timeLeft = 2.0;
    boolean chasingPlayer = false;
    boolean randomDirection = true;
    boolean randomSpeed = false;

    Image left1;
    Image left2;
    Image left3;
    Image right1;
    Image right2;
    Image right3;
    double _steps;
    final double MAX_STEPS;
    final double rest;

    double movementSpeed = 2.5 * Sprite.SCALED_SIZE;
    double changeDirectionTime = 2.0;

    public Enemy(int xUnit, int yUnit, Image img, Image dead, Board board) {
        super(xUnit, yUnit, img, board);
        _dead = dead;
        MAX_STEPS = Sprite.SCALED_SIZE * Board.TILE_WIDTH / movementSpeed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;
    }

    @Override
    public void update(double s) {
        _animate += s * 1000;
        if (_dying) {
            timeLeft -= s;
            if (timeLeft < 0) _alive = false;
        }
        if (!_dying) calculateMove(s);
        movable(x, y);
        chooseSprite(s);
        Bomber b = _board.getPlayer();
        if (!_dying)
            if (Math.abs(b.getX() - x) < Sprite.SCALED_SIZE * 12 / 16.0 && Math.abs(b.getY() - y) < Sprite.SCALED_SIZE * 15 / 16.0) {
                b.kill();
            }
        ;

    }

    private void calculateMove(double s) {
        Bomber _player = _board.getPlayer();
        Enemy _e = this;
        int xa = 0, ya = 0;
        if (chasingPlayer) {
            {
                if (_player == null)
                    _direction = getRandom(0, 3);

                int vertical = getRandom(1, 2);

                if (vertical == 1) {
                    int v;
                    {
                        if (_player.getXTile() < _e.getXTile())
                            v = 2;
                        else if (_player.getXTile() > _e.getXTile())
                            v = 0;
                        else
                            v = -1;
                    }
                    if (v != -1)
                        _direction = v;
                    else {
                        if (_player.getYTile() < _e.getYTile())
                            _direction = 1;
                        else if (_player.getYTile() > _e.getYTile())
                            _direction = 3;
                        else
                            _direction = -1;
                    }

                } else {
                    int h;

                    if (_player.getYTile() < _e.getYTile())
                        h = 1;
                    else if (_player.getYTile() > _e.getYTile())
                        h = 3;
                    else
                        h = -1;
                    if (h != -1)
                        _direction = h;
                    else {
                        if (_player.getXTile() < _e.getXTile())
                            _direction = 2;
                        else if (_player.getXTile() > _e.getXTile())
                            _direction = 0;
                        else
                            _direction = -1;
                    }
                }
            }

        } else {
            if (_steps <= 0) {
                _direction = getRandom(0, 3);
                _steps = MAX_STEPS;
            }
        }

        if (_direction == 0) xa++;
        if (_direction == 2) xa--;
        if (_direction == 3) ya++;
        if (_direction == 1) ya--;


        if (movable(x + xa * movementSpeed * s, y + ya * movementSpeed * s)) {
            _steps -= 0.1 + rest;
            move(xa * movementSpeed * s, ya * movementSpeed * s);
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }


    }

    private void move(double xStep, double yStep) {

        x += xStep;
        y += yStep;

    }

    public boolean canMove(double xx, double yy) {
        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 15.0 / 16.0;
        return
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this);
    }

    @Override
    public boolean movable(double xx, double yy) {
        double xStep = xx - x;

        double yStep = yy - y;
        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 15.0 / 16.0;
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

        return
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this);
    }

    protected void calculateDirection(double s) {
        changeDirectionTime -= s;
        if (changeDirectionTime < 0) {
            _direction = getRandom(0, 3);
            changeDirectionTime = getRandom(3.0, 6.0);
        }
    }

    private int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private double getRandom(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    protected void calculateSpeed(double s) {

    }


    public void chooseSprite(double s) {


        if (_animate > 10000) _animate = 0;
        if (!_dying) {
            int m = 0;
            if (_animate % 800 < 200) m = 0;
            else if (_animate % 800 < 400) m = 1;
            else if (_animate % 800 < 600) m = 2;

            else m = 3;
            if (_direction != 0)
                switch (m) {
                    case 0: {
                        _img = left1;
                        break;
                    }
                    case 1:
                    case 3: {
                        _img = left2;
                        break;
                    }
                    case 2: {
                        _img = left3;
                        break;
                    }
                }
            if (_direction != 2)
                switch (m) {
                    case 0: {
                        _img = right1;
                        break;
                    }
                    case 1:
                    case 3: {
                        _img = right2;
                        break;
                    }
                    case 2: {
                        _img = right3;
                        break;
                    }
                }
        } else {
            if (_animate % 2000 < 500) _img = _dead;
            else if (_animate % 2000 < 1000) _img = Sprite.mob_dead1.getFxImage();
            else if (_animate % 2000 < 1500) _img = Sprite.mob_dead2.getFxImage();
            else _img = Sprite.mob_dead3.getFxImage();
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) ((Bomber) e).kill();
        return true;
    }
}
