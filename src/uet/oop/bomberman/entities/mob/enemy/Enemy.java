package uet.oop.bomberman.entities.mob.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;

public abstract class Enemy extends Mob {
    Image _dead;
    boolean dying = false;
    double timeLeft = 1.0;

    public Enemy(int xUnit, int yUnit, Image img, Image dead) {
        super(xUnit, yUnit, img);
        _dead = dead;
    }

    @Override
    public void update(double s) {
        if (dying) {
            timeLeft -= s;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) ((Bomber) e).kill();
        return true;
    }
}
