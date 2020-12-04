package uet.oop.bomberman.entities.mob.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.balloom_left1.getFxImage(), Sprite.balloom_dead.getFxImage());
    }

    @Override
    public void update(double s) {

    }

    public void kill() {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
