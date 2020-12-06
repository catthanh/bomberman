package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Convert;

public class Flame extends Entity {
    private boolean _last;
    private int _animate = 0;
    private double _timeLeft = 0.5;
    Board _board;
    private int _direction;

    /**
     * @param xUnit
     * @param yUnit
     * @param direction 0 - right,
     *                  1 - up,
     *                  2 - left,
     *                  3 - down,
     *                  4 - center,
     */
    public Flame(int xUnit, int yUnit, int direction, boolean last, Board board) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;

        _board = board;
        _last = last;
        _direction = direction;
        if (last)
            switch (_direction) {
                case 0: {
                    this._img = Sprite.explosion_horizontal_right_last.getFxImage();
                    break;
                }
                case 1: {
                    this._img = Sprite.explosion_vertical_top_last.getFxImage();
                    break;
                }
                case 2: {
                    this._img = Sprite.explosion_horizontal_left_last.getFxImage();
                    break;
                }
                case 3: {
                    this._img = Sprite.explosion_vertical_down_last.getFxImage();
                    break;
                }
                case 4: {
                    this._img = Sprite.bomb_exploded.getFxImage();
                    break;
                }

            }
        if (!last)
            switch (_direction) {
                case 0:
                case 2: {
                    this._img = Sprite.explosion_horizontal.getFxImage();
                    break;
                }
                case 1:
                case 3: {
                    this._img = Sprite.explosion_vertical.getFxImage();
                    break;
                }
                case 4: {
                    this._img = Sprite.bomb_exploded.getFxImage();
                    break;
                }

            }
    }

    @Override
    public void update(double s) {
        animate(s);
        _timeLeft -= s;

    }

    public double getTimeLeft() {
        return _timeLeft;
    }

    private void animate(double s) {
        int m = 0;// sprite order
        _animate += s * 1000;
        if (_animate > 10000) _animate = 0;
        if (_animate % 500 < 500 / 4) m = 0;
        else if (_animate % 500 < 500 * 2 / 4) m = 1;
        else if (_animate % 500 < 500 * 3 / 4) m = 2;
        else if (_animate % 500 < 500) m = 3;
        if (m == 0) {
            if (_last)
                switch (_direction) {
                    case 0: {
                        this._img = Sprite.explosion_horizontal_right_last.getFxImage();
                        break;
                    }
                    case 1: {
                        this._img = Sprite.explosion_vertical_top_last.getFxImage();
                        break;
                    }
                    case 2: {
                        this._img = Sprite.explosion_horizontal_left_last.getFxImage();
                        break;
                    }
                    case 3: {
                        this._img = Sprite.explosion_vertical_down_last.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded.getFxImage();
                        break;
                    }

                }
            if (!_last)
                switch (_direction) {
                    case 0:
                    case 2: {
                        this._img = Sprite.explosion_horizontal.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this._img = Sprite.explosion_vertical.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded.getFxImage();
                        break;
                    }

                }
        }
        if (m == 1 || m == 3) {
            if (_last)
                switch (_direction) {
                    case 0: {
                        this._img = Sprite.explosion_horizontal_right_last1.getFxImage();
                        break;
                    }
                    case 1: {
                        this._img = Sprite.explosion_vertical_top_last1.getFxImage();
                        break;
                    }
                    case 2: {
                        this._img = Sprite.explosion_horizontal_left_last1.getFxImage();
                        break;
                    }
                    case 3: {
                        this._img = Sprite.explosion_vertical_down_last1.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded1.getFxImage();
                        break;
                    }

                }
            if (!_last)
                switch (_direction) {
                    case 0:
                    case 2: {
                        this._img = Sprite.explosion_horizontal1.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this._img = Sprite.explosion_vertical1.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded1.getFxImage();
                        break;
                    }

                }
        }
        if (m == 2) {
            if (_last)
                switch (_direction) {
                    case 0: {
                        this._img = Sprite.explosion_horizontal_right_last2.getFxImage();
                        break;
                    }
                    case 1: {
                        this._img = Sprite.explosion_vertical_top_last2.getFxImage();
                        break;
                    }
                    case 2: {
                        this._img = Sprite.explosion_horizontal_left_last2.getFxImage();
                        break;
                    }
                    case 3: {
                        this._img = Sprite.explosion_vertical_down_last2.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded2.getFxImage();
                        break;
                    }

                }
            if (!_last)
                switch (_direction) {
                    case 0:
                    case 2: {
                        this._img = Sprite.explosion_horizontal2.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this._img = Sprite.explosion_vertical2.getFxImage();
                        break;
                    }
                    case 4: {
                        this._img = Sprite.bomb_exploded2.getFxImage();
                        break;
                    }

                }
        }

    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Mob) ((Mob) e).kill();
        return true;
    }
}
