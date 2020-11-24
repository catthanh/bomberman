package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class WallTile extends Entity {

    public WallTile(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(double s) {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
