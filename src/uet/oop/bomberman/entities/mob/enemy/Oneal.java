package uet.oop.bomberman.entities.mob.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int xUnit, int yUnit, Board board) {
        super(xUnit, yUnit, Sprite.oneal_right1.getFxImage(), Sprite.oneal_dead.getFxImage(), board);
        left1 = Sprite.oneal_left1.getFxImage();
        left2 = Sprite.oneal_left2.getFxImage();
        left3 = Sprite.oneal_left3.getFxImage();
        right1 = Sprite.oneal_right1.getFxImage();
        right2 = Sprite.oneal_right2.getFxImage();
        right3 = Sprite.oneal_right3.getFxImage();
        chasingPlayer = true;
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
