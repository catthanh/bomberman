package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

public class PortalTile extends Tile {
    public static boolean passable = false;
    public static boolean passed = false;

    public PortalTile(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.portal.getFxImage();
    }

    @Override
    public void update(double s) {

    }

    @Override
    public void render(Camera camera) {
        camera.drawImage(Sprite.grass.getFxImage(), (int) x, (int) y);
        super.render(camera);
    }

    @Override
    public boolean collide(Entity e) {
        if (passable) passed = true;
        return true;
    }
}
