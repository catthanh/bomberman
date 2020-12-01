package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Mob extends Entity {
    protected int _direction = -1;
    protected boolean _moving = false;
    protected boolean _alive = true;

    public Mob(int xUnit, int yUnit, Image img) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        _img = img;
    }


    @Override
    public abstract boolean collide(Entity e);
}
