package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, Sprite.balloom_left1.getFxImage());
    }

    @Override
    public void update(double s) {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
