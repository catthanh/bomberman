package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.tile.Tile;

public abstract class ItemTile extends Tile {
    public ItemTile(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    public abstract void activeItem(Bomber e);

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !_destroyed) {
            activeItem((Bomber) e);
            destroy();
        }
        return true;
    }

    @Override
    public void destroy() {
        _destroyed = true;
    }
}
