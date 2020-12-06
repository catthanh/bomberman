package uet.oop.bomberman.entities.mob.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Mob {
    int _animate = 0;
    Image _dead;
    double timeLeft = 2.0;

    public Enemy(int xUnit, int yUnit, Image img, Image dead, Board board) {
        super(xUnit, yUnit, img, board);
        _dead = dead;
    }

    @Override
    public void update(double s) {
        if (_dying) {
            timeLeft -= s;
            if (timeLeft < 0) _alive = false;
        }
        movable(x, y);
        chooseSprite(s);
        Bomber b = _board.getPlayer();
        if (!_dying)
            if (Math.abs(b.getX() - x) < Sprite.SCALED_SIZE * 12 / 16.0 && Math.abs(b.getY() - y) < Sprite.SCALED_SIZE * 15 / 16.0) {
                b.kill();
            }
        ;

    }

    private void chooseSprite(double s) {
        if (!_dying) return;
        _animate += s * 1000;
        if (_animate > 10000) _animate = 0;
        if (_dying) {
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
