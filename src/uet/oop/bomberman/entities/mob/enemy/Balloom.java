package uet.oop.bomberman.entities.mob.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit, Board board) {
        super(xUnit, yUnit, Sprite.balloom_left1.getFxImage(), Sprite.balloom_dead.getFxImage(), board);
    }

    @Override
    public void update(double s) {
        super.update(s);
    }


    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
