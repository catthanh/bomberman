package uet.oop.bomberman.entities.mob.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit, Board board) {
        super(xUnit, yUnit, Sprite.balloom_left1.getFxImage(), Sprite.balloom_dead.getFxImage(), board);
        _direction = 2;
        left1 = Sprite.balloom_left1.getFxImage();
        left2 = Sprite.balloom_left2.getFxImage();
        left3 = Sprite.balloom_left3.getFxImage();
        right1 = Sprite.balloom_right1.getFxImage();
        right2 = Sprite.balloom_right2.getFxImage();
        right3 = Sprite.balloom_right3.getFxImage();

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
