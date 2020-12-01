package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class BrickTile extends Tile {
    double timeLeft = 1.0;
    boolean destroying = false;


    public BrickTile(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.brick.getFxImage();
    }

    @Override
    public void update(double s) {
        if (destroying) timeLeft -= s;
        if (timeLeft < 0) _destroyed = true;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }


    @Override
    public void destroy() {
        destroying = true;
    }
}
