package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

public class PortalTile extends Tile {

    public PortalTile(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.portal.getFxImage();
    }

    @Override
    public void update(double s) {

    }

    @Override
    public void render(Camera camera) {
        camera.drawImage(Sprite.grass.getFxImage(), getXTile(), getYTile());
        super.render(camera);
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
