package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class GrassTile extends Tile {

    public GrassTile(int x, int y) {
        super(x, y);
        _img = Sprite.grass.getFxImage();
    }

    @Override
    public void update(double s) {

    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
