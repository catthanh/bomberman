package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class BrickTile extends Tile {
    public BrickTile(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update(double s) {

    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
