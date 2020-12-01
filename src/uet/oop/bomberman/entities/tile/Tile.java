package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Tile extends Entity {
    protected boolean _destroyed = false;

    public Tile(int xUnit, int yUnit) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
    }

    public void destroy() {
    }


    public boolean isDestroyed() {
        return _destroyed;
    }

}
